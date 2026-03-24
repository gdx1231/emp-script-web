# dao 说明

## 1. 模块定位

`web.dao` 是本仓库的生成式数据访问层。

它的职责很明确：

- 为数据库表提供 Java 映射类
- 为映射类提供标准 CRUD DAO
- 把对象字段统一转成 `RequestValue`

它不是手写 Repository 层，而是一套明显由工具生成出来的数据库访问代码。

## 2. 结构模式

几乎每张表都会对应两类文件：

### 2.1 实体类

例如：

- `WebUser`
- `OaReq`
- `SupMain`
- `SysMessageInfo`

这些类通常：

- 继承 `ClassBase`
- 持有字段私有成员
- 提供 getter / setter
- 在 setter 中调用 `recordChanged(...)`

因此实体类除了保存数据，还会记录哪些字段发生过变化。

### 2.2 DAO 类

例如：

- `WebUserDao`
- `OaReqDao`
- `SupMainDao`
- `WxUserDao`

这些类通常：

- 继承 `ClassDaoBase<T>`
- 实现 `IClassDao<T>`
- 定义：
  - `SQL_SELECT`
  - `SQL_INSERT`
  - `SQL_UPDATE`
  - `SQL_DELETE`
  - `TABLE_NAME`
  - `KEY_LIST`
  - `FIELD_LIST`

这构成了整个代码库里非常稳定的一套 DAO 模板。

## 3. 工作方式

## 3.1 标准 CRUD

DAO 基本都提供这些方法：

- `newRecord(...)`
- `updateRecord(...)`
- `deleteRecord(...)`
- `getRecord(...)`
- `getRecords(...)`

因此上层业务代码通常直接 new 一个 DAO，再按 where 条件取数或写数。

## 3.2 对象转 `RequestValue`

每个 DAO 都会有：

- `createRequestValue(...)`

它负责把实体对象的每个字段转成 `RequestValue` 参数，并显式写出：

- 参数名
- 数据类型
- 长度

这也是本仓库数据访问与 EMP 运行时风格保持一致的关键。

## 3.3 增量更新

生成的实体类 setter 里会调用：

- `recordChanged(...)`

而 DAO 中又提供：

- `newRecord(para, updateFields)`
- `updateRecord(para, updateFields)`

配合：

- `sqlInsertChanged(...)`
- `sqlUpdateChanged(...)`

实现“只更新变更字段”的模式。

这说明这些 DAO 不只是最原始 CRUD，也支持部分字段更新。

## 3.4 自增主键处理

像 `OaReqDao` 这类表会使用：

- `executeUpdateAutoIncrement(...)`

插入后再把自增主键回写到实体对象中。

所以并不是所有表都需要调用方自己先生成主键。

## 4. 典型代码风格

从 `WebUserDao`、`OaReqDao` 等可以总结出几条明显风格：

### 4.1 SQL 常量集中写死

SQL 不是动态拼出来的，而是直接在 DAO 顶部定义成静态字符串。

### 4.2 `whereString` 查询较多

很多 `getRecords(whereString)` 都是：

```java
String sql = "SELECT * FROM XXX WHERE " + whereString;
```

这意味着调用方经常自己拼查询条件。

### 4.3 注释里会提醒注入风险

生成代码里经常写着：

- 注意过滤 `'`
- 避免 SQL 注入攻击

这也说明这里的 `whereString` 设计本身就要求调用方自己负责安全性。

## 5. 与业务层的关系

业务层通常直接依赖这些 DAO，不再额外包一层 Repository。

例如：

- `RegisterOrLogin` 直接使用 `WebUserDao`
- `ProjectManager` / `ProjectBase` 直接使用 `OaPrjDao`、`OaReqDao`
- `App` / `AppMini` 直接使用 `SupMainDao`、`WebUserLevelDao`、`WxUserDao`

所以 `web.dao` 可以看成仓库中最基础的一层持久化基建。

## 6. 适用范围

`web.dao` 主要覆盖的是仓库里高频业务表，例如：

- 用户
- 管理员
- 项目 / 任务
- 短信 / 邮件队列
- 微信用户
- 新闻 / 站点 / 标签

但复杂查询并没有完全封装在 DAO 中，很多业务类仍会直接写 SQL。

## 7. 使用与维护时要注意

1. 这是生成式代码，新增字段或改表结构时，最好保持“实体类 + DAO”成对更新。
2. `whereString` 风格很灵活，但调用方必须自己处理 SQL 安全问题。
3. 实体 setter 会记录变更字段，如果你想用增量更新，别绕开 setter 直接改内部字段。
4. `createRequestValue(...)` 里显式写了类型和长度，改字段类型时这里要同步改。
5. 不要把这些 DAO 误认为完整领域层，它们更像“表映射 + 通用 CRUD”。

## 8. 总结

可以把 `web.dao` 理解为：

**一套围绕数据库表自动生成的 Java 映射与 CRUD 访问层**

其中：

- 实体类继承 `ClassBase`
- DAO 继承 `ClassDaoBase`
- `RequestValue` 是统一参数容器
- 变化字段跟踪用于增量更新

如果后续继续扩展仓库的数据访问层，最好保持这套生成式风格一致，而不要在相邻表上混入完全不同的 DAO 设计。
