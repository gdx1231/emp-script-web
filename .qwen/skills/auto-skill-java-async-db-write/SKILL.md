---
name: java-async-db-write
description: Review and fix common bugs in async Java database write patterns — connection lifecycle, thread safety, thread pool usage, bounded queues, memory control
source: auto-skill
extracted_at: '2026-06-27T00:00:00Z'
---

# Java Async DB Write — 常见 Bug 与修复模式

## Checklist：审查异步数据库写入代码时逐项检查

### 1. 连接生命周期

- **提前关闭**：`cnn.close()` 之后又使用同一个连接 → 必须确保 close 在所有使用之后
- **重复关闭**：外层 finally 和内层 finally 都 close → 只在**最终使用者**的 finally 中 close
- **正确做法**：连接在哪个线程最终使用，就在那个线程的 finally 中 close

### 2. 线程安全

- **实例字段跨线程共享**：`rv`、`sqls` 等实例字段在主线程写、子线程读 → 改为局部变量通过参数传递
- **Hand-off 模式安全的前提**：主线程写入完毕后才 submit 子线程，且主线程之后不再触碰这些对象

### 3. 线程池

- **禁止每次调用 `newSingleThreadExecutor()`**：高并发下线程爆炸，OOM 风险
- **禁止 `Executors.newFixedThreadPool`**：内部用无界 `LinkedBlockingQueue`，任务堆积时每个任务持有的 DB 连接（~1-10MB）会导致内存膨胀
- **使用有界队列的 `ThreadPoolExecutor`**：
  ```java
  // 队列容量应 ≤ 数据库连接池大小，默认 33
  public static int EXECUTOR_CORE_POOL_SIZE = 2;
  public static int EXECUTOR_QUEUE_CAPACITY = 33;

  private static final ExecutorService EXECUTOR = new ThreadPoolExecutor(
      EXECUTOR_CORE_POOL_SIZE,
      Math.max(EXECUTOR_CORE_POOL_SIZE, Runtime.getRuntime().availableProcessors()),
      60L, TimeUnit.SECONDS,
      new LinkedBlockingQueue<>(EXECUTOR_QUEUE_CAPACITY),
      r -> {
          Thread t = new Thread(r, "task-name");
          t.setDaemon(true);  // JVM退出时不等待
          return t;
      },
      (r, executor) -> LOGGER.warn("Queue full, task dropped. Size: {}", executor.getQueue().size()));
  ```
- **队列容量选择**：每个排队任务持有 JDBC 连接（1~10MB），4096 队列 = 4~40GB 峰值内存。队列深度应匹配连接池大小（通常 10~100）
- **可配参数用 `public static` 字段**：与项目中已有配置风格一致（如 `CONN_CONFIG_NAME`），方便外部在类加载前调整

### 4. 阻塞 vs Fire-and-forget

- 日志、审计等**不需要返回结果**的操作：用 `execute(Runnable)`，不要 `future.get()`
- 需要结果时：`future.get()` 必须有超时，且超时值要合理
- Fire-and-forget 模式异常在子线程 catch + log，不传播

### 5. 推荐的 fire-and-forget 模板

```java
EXECUTOR.execute(() -> {
    try {
        // 使用连接执行操作
        doWork(cnn, data);
    } catch (Exception err) {
        LOGGER.error(err.getMessage());
    } finally {
        cnn.close();
    }
});
// 主线程立即返回，不阻塞
```

### 6. AtomicInteger 陷阱

- `compareAndSet(expected, newValue)` 只在当前值等于 expected 时更新，**第二次调用静默失败**
- 需要无条件更新时用 `set(newValue)`
- 典型错误：`DETAIL_MAX_SIZE_ATOM.compareAndSet(MAX_DETAIL_LENGTH, size)` — 多次调用只有第一次生效

### 7. 静态 Map 未初始化

- `private static Map<K,V> MAP;` 声明后未赋值 → 首次访问 NPE
- 修复：`private static final Map<K,V> MAP = new ConcurrentHashMap<>();`
- 并发场景需考虑线程安全（`ConcurrentHashMap` 或加锁）

### 8. 线程安全懒初始化（缓存场景）

静态 Map 缓存需要并发安全的初始化：

```java
private static final Map<Integer, ExpensiveResource> CACHE = new ConcurrentHashMap<>();

private static ExpensiveResource get(int key) throws Exception {
    ExpensiveResource cached = CACHE.get(key);
    if (cached != null) {
        return cached;
    }
    // 创建新实例（可能多个线程同时到达这里）
    DataConnection cnn = new DataConnection("", null);
    ExpensiveResource res = new ExpensiveResource(cnn, ...);

    ExpensiveResource prev = CACHE.putIfAbsent(key, res);
    if (prev != null) {
        // 另一个线程先放入了，关闭当前多余创建的连接
        cnn.close();
        return prev;
    }
    return res;
}
```

关键：`putIfAbsent` 返回已存在的值，如果非 null 说明竞态失败，**必须清理多余创建的资源**（如 close 连接）。

### 9. 错误字符串被当作有效数据返回

```java
// ❌ 错误：调用方会把 "太多次的尝试" 当正常 UID 使用
public String createUid() {
    if (retries > 10) return "太多次的尝试";
    ...
}

// ✅ 正确：失败时抛异常，调用方无法把错误信息当数据使用
public String createUid() {
    if (retries > 10) throw new RuntimeException("生成UID失败，超过最大重试次数");
    ...
}
```

**检查模式**：任何方法的返回值会被下游当作数据入库时，确保失败路径不会返回看起来像合法数据的字符串。

### 10. `ThreadLocalRandom` 替代 `Math.random()`

生成随机 ID/编号时：

```java
// ❌ 绕弯且随机性不足
String a = Math.random() * max + "";
int b = Integer.parseInt(a.split("\\.")[0]);

// ✅ 简洁、线程安全、随机性更好
ThreadLocalRandom.current().nextInt(max)
```

### 11. 被调用方关闭调用方的连接

方法接收外部传入的 `DataConnection` 并在内部 `close()` 它，导致调用方后续使用该连接时失败：

```java
// ❌ callee 关闭了 caller 的连接
private void initData(DataConnection conn, int supId) {
    conn.executeUpdate(sql);
    conn.close();  // 调用方的 conn 被关闭！
}

// caller:
initData(conn, supId);
DTTable tb = DTTable.getJdbcTable(sql, conn);  // conn 已关闭，失败

// ✅ 被调用方不关闭外部传入的连接，由调用方管理生命周期
private void initData(DataConnection conn, int supId) {
    conn.executeUpdate(sql);
    // 不 close
}
```

**规则**：方法参数传入的 `DataConnection`，由**调用方**负责关闭，被调用方只管使用。

### 12. 成功路径遗漏关闭连接

所有错误分支都正确 `close()` 了连接，但**正常返回路径**遗漏了：

```java
// ❌ 成功路径未关闭
public String createDoc() {
    _Cnn = new DataConnection();
    if (error) { _Cnn.close(); return err; }      // ✓ 错误路径关闭了
    if (empty) { _Cnn.close(); return msg; }       // ✓ 空结果关闭了
    try { doWork(); } catch (Exception e) {
        _Cnn.close(); return err;                   // ✓ 异常路径关闭了
    }
    return result;                                  // ❌ 成功路径未关闭
}

// ✅ 成功路径也要关闭
    _Cnn.close();
    return result;
```

**审查方法**：画一个方法的所有 `return` 路径，逐一检查每条路径上连接是否都被关闭。

### 13. 审查清单（扩展）

审查任何 DB 写入类时额外检查：
- [ ] 静态资源（Map、Cache）是否已初始化
- [ ] `DataConnection` 在方法内创建后是否在所有路径都关闭
- [ ] SQL 是否用参数化查询而非字符串拼接
- [ ] 缓存的并发初始化是否用 `putIfAbsent` + 竞态失败资源清理
- [ ] 方法失败时是否返回了会被下游当合法数据使用的错误字符串
- [ ] 随机数生成是否使用 `ThreadLocalRandom` 而非 `Math.random()` + string 操作
- [ ] 方法内声明但未使用的变量（死代码）
- [ ] 被调用方是否关闭了调用方传入的连接（应只由调用方管理生命周期）
- [ ] 方法的所有 return 路径（包括成功路径）是否都关闭了连接
