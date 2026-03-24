# project 说明

## 1. 模块定位

`project` 模块用于把项目主数据、项目任务、任务层级、模板配置和进度标记组织到一套统一结构中。

它不是通用项目管理产品，而是围绕本仓库既有表结构实现的“项目 + 任务树”能力，核心依赖这些表：

- `OA_PRJ`：项目主表
- `OA_REQ`：任务 / 节点主表
- `OA_PRJ_REQ`：项目与任务关系表
- `OA_REQ_LINK`：任务前后依赖关系
- `OA_PRJ_MARK`：项目时间标记
- `OA_PRJ_CHECK_LAST`：模板检查与刷新相关记录

这一模块的特点是：

- 既支持直接创建项目
- 也支持按 XML 模板批量生成 / 更新任务树
- 任务树与项目关系是持久化到数据库中的

## 2. 关键类

### 2.1 `ProjectBase`

项目能力的基础类。

主要职责：

- 从 `RequestValue` 中读取当前 `g_sup_id`、`g_adm_id`
- 创建或获取 `OA_PRJ`
- 维护当前项目主对象 `OaPrj`
- 创建 `ProjectManager`

典型入口方法：

- `createOrGetProject(prjRefTable, prjRefId)`
- `createOrGetProject(prjRefTable, prjRefId, xmlTemplate)`

这说明项目本身通常带有来源引用信息，而不是孤立创建。

### 2.2 `ProjectManager`

`project` 模块最核心的运行类之一。

主要负责：

- 查询项目下的任务
- 更新任务颜色、时间、层级
- 维护汇总任务时间
- 移动任务节点
- 维护 `OA_REQ_LINK`
- 输出项目任务与依赖数据

从代码可见，它大量直接操作：

- `OA_REQ`
- `OA_PRJ_REQ`
- `OA_REQ_LINK`
- `OA_REQ_DEPT`

所以它更像项目任务树的“数据库驱动服务层”。

### 2.3 `ProjectNodes`

用于把项目中的 `OA_REQ` 数据组织成内存里的任务树。

主要职责：

- 加载项目已有的 `OA_REQ`
- 建立 `REQ_ID -> 节点` 映射
- 建立 `REQ_TYPE -> 节点列表` 映射
- 建立父子关系
- 标记哪些节点未再被模板使用，并把它们更新为 `OA_REQ_DEL`

它是模板同步逻辑的重要基础。

### 2.4 `ProjectNodeData`

单个项目节点的包装对象。

封装内容：

- `OaReq`
- 父节点
- 子节点列表
- `isAlive`

其中 `isAlive` 很关键：模板刷新完成后，没有被重新激活的节点会被标记为未使用并删除。

### 2.5 `ProjectTemplate`

项目模板定义类。

它从 XML 模板中读取：

- 模板描述
- 根任务列表
- 所有任务映射
- `main_data_sql`
- `mark` 标记定义

也就是说，项目模板不是写死在 Java 里，而是外部 XML 驱动。

### 2.6 `ProjectTemplateTask` / `ProjectTemplateMark`

模板中的单个任务与时间标记定义。

作用：

- 定义任务 ID、层级、颜色、重复策略
- 定义数据查询 SQL
- 定义生成节点时需要的字段与规则
- 定义项目时间节点（mark）

### 2.7 `PrjFromTmpBase`

按模板生成 / 更新项目任务树的核心类。

它负责：

- 执行 `main_data_sql`
- 加载项目现有节点
- 根据模板递归创建或更新 `OA_REQ`
- 处理重复任务（repeat）
- 更新项目标记
- 标记未使用节点为删除

它是模板化项目能力的核心执行器。

### 2.8 `ProjectMain`

`ProjectBase` 的具体入口类之一。

典型能力：

- 从业务数据创建项目
- 从分组 / 团相关场景获取项目
- 加载指定模板，生成 `PrjGroupFromTmp`

说明这个模块和业务表之间通常是强绑定的，而不是完全独立运行。

## 3. 数据模型理解

可以把这块的数据关系理解为：

### 项目层

- `OA_PRJ`

### 任务层

- `OA_REQ`

### 项目与任务关系

- `OA_PRJ_REQ`

### 任务依赖关系

- `OA_REQ_LINK`

### 项目标记

- `OA_PRJ_MARK`

### 模板刷新检查信息

- `OA_PRJ_CHECK_LAST`

其中 `OA_REQ.REQ_TYPE` 很重要，它被用于映射模板里的 `taskId`。

## 4. 模板驱动机制

这是 `project` 模块最核心的设计。

### 4.1 模板来源

`ProjectTemplate.loadTemplate(...)` 从 XML 文件中加载模板。

模板内容主要包括：

- `<task>`
- `<mark>`
- `<main_data_sql>`

### 4.2 主数据 SQL

模板中的 `main_data_sql` 会先执行，返回的数据表会被缓存到 `PrjFromTmpBase.tbs_` 中，作为后续任务生成的全局变量来源。

### 4.3 递归生成任务树

`PrjFromTmpBase.recursiveNewOrUpdateOaReqs(...)` 会：

1. 遍历当前层模板任务
2. 判断是否需要刷新
3. 判断是否为重复任务
4. 查询或创建对应 `OA_REQ`
5. 更新任务字段
6. 递归处理子任务

### 4.4 重复任务

如果某个模板任务是 repeat 类型，则会：

- 执行 `dataRepeatSql`
- 按每一行数据生成一份节点
- 用 `REF_TAB` 保存循环键值

这意味着模板任务并不是只能生成单节点，也可以按业务数据批量生成一组子任务。

### 4.5 未使用节点清理

模板执行后，如果某些旧节点没有再被标记为 `alive`，则会被更新为：

- `REQ_STATUS = 'OA_REQ_DEL'`

这让模板刷新可以同时做“增量更新 + 无效节点清理”。

## 5. 项目任务树的工作方式

### 5.1 节点组织

节点树通过 `REQ_PID` 建立父子关系。

### 5.2 汇总节点

当叶子任务更新后，`ProjectManager` 会向上更新父节点时间汇总。

### 5.3 任务移动

`ProjectManager` 提供任务移动相关逻辑，用于调整层级关系。

### 5.4 任务依赖

`OA_REQ_LINK` 用于维护任务依赖边。

`ProjectManager` 负责：

- 创建依赖
- 删除依赖
- 查询项目下所有依赖关系

所以项目结构不仅是树，还支持额外的依赖图。

## 6. 典型调用链

### 6.1 创建或获取项目

`ProjectBase.createOrGetProject(...)`

### 6.2 加载模板

`ProjectTemplate.loadTemplate(...)`

### 6.3 按模板刷新任务

`PrjFromTmpBase.checkOaReqsData()`

### 6.4 构建内存节点树

`ProjectNodes.initData()`

### 6.5 更新汇总时间或依赖

`ProjectManager`

## 7. 配置与外部依赖

### 7.1 外部 XML 模板

这是 `project` 模块的关键外部依赖之一。

没有模板 XML，就无法发挥模板化项目能力。

从源码可以直接看出，典型模板路径包括：

- `config/ProjectTemplate/Group.xml`
- `config/ProjectTemplate/AnnualSales.xml`

这些模板文件在当前仓库快照中并未出现，因此更像部署环境或外部工程提供的运行期配置。

### 7.2 业务来源表

项目通常不是孤立存在，而是通过：

- `PRJ_REF_TABLE`
- `PRJ_REF_ID`

与业务表关联。

### 7.3 `RequestValue`

项目上下文强依赖：

- `g_sup_id`
- `g_adm_id`
- 其他业务参数

模板执行、SQL 查询和项目创建都以此为上下文。

## 8. 使用与维护时要注意

1. `project` 模块是强表结构依赖模块，改表字段前要先检查模板和 Java 是否同时依赖。
2. 模板里的 `taskId` 与 `OA_REQ.REQ_TYPE` 是一组关键映射，不能随意改名。
3. 模板刷新不是只新增节点，也会删除不再使用的旧节点。
4. 重复任务依赖循环键（如 `REF_TAB`）识别同一类节点，相关 SQL 改动要谨慎。
5. `ProjectManager` 直接写 SQL 的地方较多，改逻辑时要留意任务树、汇总时间和链接关系是否同时一致。

## 9. 总结

可以把 `project` 模块理解为：

**一个基于数据库表和 XML 模板驱动的项目任务树引擎**

其中：

- `ProjectBase` 管项目主对象
- `ProjectManager` 管任务树与依赖关系
- `ProjectNodes` 管内存树结构
- `ProjectTemplate` / `ProjectTemplateTask` / `ProjectTemplateMark` 管模板定义
- `PrjFromTmpBase` 负责把模板同步到真实项目任务数据

如果后续要扩展项目能力，优先沿用“模板驱动 + `OA_REQ` 节点树 + `OA_REQ_LINK` 依赖关系”的现有模式。
