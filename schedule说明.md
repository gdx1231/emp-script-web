# schedule 说明

## 1. 模块定位

`schedule` 模块用于根据外部 XML 配置批量执行 SQL 任务，并把执行结果记录到调度主表和明细表中。

它不是基于 Quartz 的 Java 任务框架，而是一个：

- **配置驱动**
- **SQL 驱动**
- **结果入库**

的轻量调度执行器。

需要特别强调的是：它本身**不负责定时触发**。模块只负责读取配置并执行，真正“何时运行”需要由外部程序、任务调度器或宿主系统显式调用。

## 2. 关键类

### 2.1 `Schedules`

调度集合与加载入口。

主要职责：

- 维护所有 `Schedule`
- 从 XML 中加载调度配置
- 设置数据库连接名
- 逐个运行调度

关键点：

- 默认配置路径常量：`/schedule.xml`
- 采用单例缓存方式

### 2.2 `Schedule`

单个调度任务的核心执行类。

一个 `schedule` 节点包含三类关键内容：

- `checkValue`
- `checkSql`
- 多个 `execute`

其中：

- `checkValue`：先执行，得到检查值
- `checkSql`：再执行，得到需要处理的数据行
- `execute`：对每一行数据执行的 SQL

### 2.3 `Execute`

单个执行子步骤的简单封装对象。

封装字段：

- `id`
- `name`
- `sql`

它本身不执行逻辑，只保存调度子步骤定义。

## 3. 配置方式

`Schedules.loadCfgs(url)` 会读取 XML，并解析所有 `<schedule>` 节点。

单个 `<schedule>` 的典型结构由代码可推断为：

- `id`
- `name`
- `<checkValue>`
- `<checkSql>`
- `<para>`
- `<execute>`

也就是说，调度任务完全由 XML 描述，而不是在 Java 中逐条写死。

## 4. 执行流程

`Schedule.runSchedule()` 的典型处理顺序如下：

1. 检查 `checkSql` 是否为空
2. 检查 `checkValue` 是否为空
3. 检查 `execute` 列表是否为空
4. 执行 `checkValue`
5. 取最后一个结果表的第一行第一列作为 `schedule_check_value`
6. 把 `schedule_check_value` 写入 `RequestValue`
7. 执行 `checkSql`
8. 如果无数据，则本次调度结束
9. 记录 `schedule_main`
10. 对 `checkSql` 返回的每一行依次执行所有 `execute`
11. 每个 `execute` 结果记录到 `schedule_sub`

这说明它是一个典型的：

**先判断是否需要执行 -> 再按结果行批量执行子 SQL**

的调度模式。

## 5. `RequestValue` 的作用

`schedule` 模块也延续了本仓库一贯的设计：大量依赖 `RequestValue` 作为运行时上下文。

调度执行过程中，`RequestValue` 中会放入：

- `schedule_id`
- `schedule_name`
- `schedule_check_sql`
- `schedule_check_value_sql`
- `schedule_check_value`
- `schedule_main_index`
- `schedule_sub_id`
- `schedule_sub_name`
- `schedule_sub_sql`
- `schedule_sub_result`
- `schedule_sub_error`

此外，`checkSql` 返回的每行字段也会被合并到执行时的 `RequestValue` 中。

因此：

- `execute` SQL 可以直接使用前面步骤产生的变量
- 参数传递是通过 `RequestValue` 串起来的

## 6. `<para>` 参数机制

`Schedule.init(...)` 会解析 `<para>` 节点。

每个参数包含：

- `name`
- `value`
- `dataType`

这些参数会：

- 被加入 `paras`
- 被加入当前 `RequestValue`
- 并且被标记为 `PageValueTag.SESSION`

代码里注释写得很明确：这样做是为了便于权限判断。

也就是说，调度执行环境中，不只是 SQL 参数，还模拟了一部分“会话态参数”。

## 7. 结果记录

## 7.1 主记录：`schedule_main`

当 `checkSql` 有返回数据时，会先写一条主记录。

记录内容包括：

- 调度 ID
- 调度名称
- 检查值
- 检查时间
- 检查 SQL
- 结果
- 错误信息

## 7.2 子记录：`oa_work.schedule_sub`

每执行一个 `execute`，就写一条子记录。

记录内容包括：

- 主调度索引
- 子步骤 ID
- 子步骤名称
- 子步骤 SQL
- 子步骤结果
- 执行时间
- 错误信息

这样后续可以从数据库追踪整个调度执行链路。

## 8. 典型使用场景

适合用于：

- 周期性 SQL 检查
- 数据清理
- 批量状态推进
- 定时同步
- 检查后对命中记录批量执行多段 SQL

不太适合：

- 复杂 Java 业务流程编排
- 需要大量外部 API 调用的任务
- 强事务、多步骤业务补偿流程

因为它本质上是“SQL 驱动调度器”。

## 9. 使用与维护时要注意

1. 一个调度至少要有 `checkValue`、`checkSql` 和一个 `execute`。
2. `checkValue` 与 `checkSql` 都支持多 SQL 执行，但最终只取最后一个结果表。
3. `execute` 是按 `checkSql` 返回的每一行逐个执行的，数据量大时要注意 SQL 成本。
4. 子步骤执行时会克隆 `RequestValue`，避免不同步骤互相污染参数。
5. 结果记录写到了 `schedule_main` 和 `oa_work.schedule_sub`，排查问题时应先看这两张表。
6. 连接池名称来自 `Schedules.connStr`，部署时要确认调度跑的是正确的数据源。

## 10. 总结

可以把 `schedule` 模块理解为：

**一个基于 XML 配置和 SQL 执行链的轻量调度引擎**

其中：

- `Schedules` 负责加载和运行全部调度
- `Schedule` 负责单个调度的检查、执行与记录
- `Execute` 负责保存每个子步骤定义

如果后续新增调度能力，优先沿用现有模式：

- XML 配置
- `RequestValue` 传参
- `checkValue -> checkSql -> execute` 三段式执行
- `schedule_main / schedule_sub` 结果留痕
