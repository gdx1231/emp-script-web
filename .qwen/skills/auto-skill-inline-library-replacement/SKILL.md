---
name: inline-library-replacement
description: Replace moderate-complexity external libraries (Guava Cache, ical4j, etc.) with JDK built-in alternatives — ConcurrentHashMap/Timer, raw string builders, and when to inline vs keep the dependency
source: auto-skill
extracted_at: '2026-06-27T10:00:00.000Z'
---

# 依赖内联化：用 JDK 内置替换外部库

当外部库只有一两个类被使用，且功能可用 JDK 自带 API 轻松实现时，将其替换为零依赖实现。

## 决策矩阵

| 条件 | 内联 | 保留 |
|---|---|---|
| 使用者 ≤ 1-2 个文件 | ✓ | |
| 依赖有大量传递依赖 | ✓ | |
| 功能可用 ≤100 行 JDK 代码实现 | ✓ | |
| 涉及复杂算法/协议 | | ✓ |
| 是安全/加密库（审计要求） | | ✓ |
| 使用者遍布多个模块 | | ✓ |

## 案例 1：Guava Cache → ConcurrentHashMap + ScheduledExecutor

### 原代码（Guava）
```java
Cache<String, Value> cache = CacheBuilder.newBuilder()
    .maximumSize(100)
    .expireAfterAccess(10, TimeUnit.MINUTES)
    .removalListener((RemovalListener<String, Value>) n ->
        LOGGER.info("{} removed: {}", n.getKey(), n.getCause()))
    .recordStats()
    .build();

// 使用
cache.put(key, value);
Value v = cache.getIfPresent(key);
```

### 替换实现

```java
public class SimpleCache {
    private final ConcurrentHashMap<String, Entry> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService cleaner;
    private final long ttlNanos;

    private static class Entry {
        final Object value;
        volatile long expireAt;
        Entry(Object value, long ttlNanos) {
            this.value = value;
            this.expireAt = System.nanoTime() + ttlNanos;
        }
    }

    public SimpleCache(long ttl, TimeUnit unit) {
        this.ttlNanos = unit.toNanos(ttl);
        cleaner = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "cache-cleaner");
            t.setDaemon(true);
            return t;
        });
        cleaner.scheduleAtFixedRate(this::evict, 1, 1, TimeUnit.MINUTES);
    }

    public void put(String key, Object value) {
        cache.put(key, new Entry(value, ttlNanos));
    }

    public Object get(String key) {
        Entry e = cache.get(key);
        if (e == null) return null;
        if (e.expireAt < System.nanoTime()) {
            cache.remove(key, e);  // 原子校验后移除
            return null;
        }
        e.expireAt = System.nanoTime() + ttlNanos;  // 访问刷新 TTL
        return e.value;
    }

    private void evict() {
        long now = System.nanoTime();
        cache.forEach((k, e) -> {
            if (e.expireAt < now) cache.remove(k, e);
        });
    }
}
```

**关键点**：
- `ConcurrentHashMap.remove(key, value)` 进行原子校验，避免误删并发更新的条目
- Daemon 线程确保不阻止 JVM 退出
- `volatile expireAt` 保证跨线程可见性（非严格原子，容忍 1 分钟延迟清理）

## 案例 2：ical4j → 纯字符串拼接

当输出格式是简单文本协议（iCalendar、CSV、简单 XML/JSON），直接字符串拼接即可。

### 原代码（ical4j）
```java
Calendar calendar = new Calendar();
calendar.getProperties().add(new ProdId("..."));
calendar.getProperties().add(ImmutableVersion.VERSION_2_0);
VEvent meeting = new VEvent(start, end, subject);
meeting.getProperties().add(new Uid(...));
// ... 20+ 行构建
CalendarOutputter outputter = new CalendarOutputter();
outputter.output(calendar, outputStream);
```

### 替换实现
```java
StringBuilder ics = new StringBuilder(2048);
ics.append("BEGIN:VCALENDAR\r\n");
ics.append("VERSION:2.0\r\n");
ics.append("PRODID:-//GDXSoft//Calendar//EN\r\n");
ics.append("METHOD:REQUEST\r\n");
ics.append("BEGIN:VEVENT\r\n");
ics.append("DTSTART;TZID=Asia/Shanghai:").append(start).append("\r\n");
// ... 直接拼接
ics.append("END:VEVENT\r\n");
ics.append("END:VCALENDAR\r\n");
byte[] icsBytes = ics.toString().getBytes(StandardCharsets.UTF_8);
```

**特殊字符转义**：
```java
// iCalendar TEXT value: \n → \\n, \\ → \\\\
static String escapeIcsText(String s) {
    return s.replace("\\", "\\\\").replace("\n", "\\n").replace("\r\n", "\\n");
}

// iCalendar param value: " → '
static String escapeIcsParam(String s) {
    return s.replace("\"", "'");
}
```

**何时适用**：
- 格式规范 ≤ 1 页 RFC
- 不需要复杂对象模型（对象图遍历、验证、序列化互转）
- 输出频次低（非热路径）

## 删除前的自检

对每个待移除的依赖执行：
```bash
# 1. 确认唯一使用者
grep -r "import com.example.lib" src/main/java

# 2. 确认无反射调用
grep -r "com.example.lib" src/main/resources

# 3. 查看传递依赖树
mvn dependency:tree | grep -B2 "com.example.lib"

# 4. 删除后验证
mvn compile && mvn test -Dtest="RelevantTest"
```

## 本项目实际内联结果

| 库 | 替换方式 | 行数 | 清除的传递依赖 |
|---|---|---|---|
| Guava `32.0.0-jre` | `ConcurrentHashMap` + `ScheduledExecutorService` | ~60 | 6 (`failureaccess`, `jsr305`, `checker-qual`, `error_prone_annotations`, `j2objc-annotations`, `listenablefuture`) |
| ical4j `4.1.1` | `StringBuilder` 拼接 .ics | ~80 | 2 (`commons-codec`, `threeten-extra`) |
| commons-exec `1.5.0` | 删除（死代码 `WeiXinMediaUtils`） | 0 | 0 |
