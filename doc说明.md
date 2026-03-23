# doc 说明

## 1. 模块定位

`doc` 模块用于根据文档模板、参数表和业务数据动态生成文档内容，并支持：

- 在线展示
- 下载
- 转 PDF
- 发送邮件
- 保存到附件或业务文件表

它不是单纯的富文本模板替换器，而是一个基于数据库表、文档模板和 EMP 运行时的文档生成模块。

## 2. 关键类

### 2.1 `DocMain`

`DocMain` 是文档处理的主入口。

核心职责：

- 统一接收请求
- 根据参数决定：
  - 在线展示
  - 下载 HTML / App 页面
  - 转 PDF
  - 发送邮件
  - 保存到附件

主要入口方法：

- `handleDoc(HttpServletRequest request, HttpServletResponse response, RequestValue rv)`

它会把文档流程分发到：

- `handleShowDoc(...)`
- `handleDownload(...)`
- `handleDownloadPdf(...)`
- `handleSendEmail(...)`
- `handleSavePdf(...)`

因此它更像 `doc` 模块的总控类。

### 2.2 `DocPage`

`DocPage` 是文档页面内容生成类。

从 `DocMain` 的使用方式可知，它负责：

- 根据当前参数生成文档 HTML
- 读取主表 / 引用信息
- 输出文档标题与内容

它是文档内容生成的中间协调层。

### 2.3 `DocCreate`

`DocCreate` 是文档模板渲染核心。

主要职责：

- 加载文档模板
- 加载模板参数
- 创建文档值记录
- 生成单文档、列表文档、EWA 文档
- 处理子文档嵌套
- 使用 `HtmlControl` 执行 EWA 文档模板

它是真正把模板、参数和数据拼成文档 HTML 的关键类。

### 2.4 `DocTmp` / `DocTmpDao` / `DocTmpGrp`

这些类负责文档模板元数据。

典型职责包括：

- 文档模板主信息
- 模板分组
- 模板查询与持久化

说明 `doc` 模块的模板不是写死在代码里，而是数据库驱动。

### 2.5 `DocValMain`

用于文档值主记录的管理。

配合 `DOC_VAL_MAIN` / `DOC_VAL` 一类表结构，用于记录一次文档生成对应的参数和值。

### 2.6 `DocUtils`

文档辅助工具类。

提供能力包括：

- 将文档保存到合同或业务文件中
- 特殊占位符替换
- 例如将 `##{...}##` 这类 JSON 定义片段替换为二维码 HTML

它说明文档模板里并不只是简单变量替换，还支持特殊增强语法。

## 3. 工作方式

## 3.1 文档生成不是纯静态模板替换

`doc` 模块的生成逻辑涉及多层数据：

- 文档模板定义
- 文档参数定义
- 文档值表
- 当前请求参数 `RequestValue`
- 运行时 SQL 查询结果

因此它更像一个“文档渲染引擎”，而不是单纯模板文本替换。

## 3.2 文档类型

从 `DocCreate.create(...)` 可见，文档至少支持三种类型：

- `DOC_TYPE_ONE`
- `DOC_TYPE_LST`
- `DOC_TYPE_EWA`

其中：

- `DOC_TYPE_ONE`：单文档
- `DOC_TYPE_LST`：列表型文档
- `DOC_TYPE_EWA`：通过 `HtmlControl` 渲染的 EWA 文档

## 3.3 子文档机制

`DocCreate` 支持子文档嵌套。

它会：

- 在参数表中识别 `DOC_REF_DOCSUB`
- 读取对应子模板
- 递归生成子文档 HTML
- 把子文档结果替换到主模板里

这意味着一个最终文档可以由多个子模板拼出来。

## 3.4 EWA 文档模式

当文档类型是 `DOC_TYPE_EWA` 时，`DocCreate.createEwa()` 会：

1. 从模板上读取 `xmlname`、`itemname`、参数 SQL
2. 用 `HtmlControl.init(...)` 执行
3. 直接返回生成后的 HTML

这和仓库其他模块一样，体现出明显的 **EMP / EWA 配置驱动** 特征。

## 4. 文档输出方式

`DocMain` 支持多种输出模式：

### 4.1 在线展示

`handleShowDoc(...)` 会：

- 拼完整 HTML 页面
- 引入 EWA JS/CSS
- 引入打印样式
- 输出文档内容

### 4.2 下载

支持直接下载文档内容，也支持 App 侧特定展示模式。

### 4.3 转 PDF

`handleDownloadPdf(...)` 会：

- 先生成 HTML 文件
- 再通过 `Html2PdfByChrome` 调 Chrome headless 转 PDF
- 最后重定向到生成后的 PDF URL

说明 PDF 输出依赖本地文件与 Chrome 转换能力。

### 4.4 发送邮件

`handleSendEmail(...)` 会把文档 HTML 包装后调用 `UMail.sendHtmlMail(...)` 发送邮件。

## 5. 关键数据表与依赖

从代码可见，`doc` 模块至少依赖这些概念：

- 文档模板表
- 文档参数表：如 `BAS_DOC_PARA`
- 文档值主表：如 `DOC_VAL_MAIN`
- 文档值表：如 `DOC_VAL`
- 模板分组表：如 `BAS_DOC_TMP_GRP`

并且会与业务表联动，用于按业务主键生成对应文档。

## 6. 典型调用链

### 6.1 在线预览

`DocMain.handleDoc(...)`
-> `DocPage.createDocContent(...)`
-> `DocCreate.create(...)`
-> 输出 HTML

### 6.2 EWA 文档

`DocCreate.createEwa()`
-> `HtmlControl.init(...)`
-> 返回 EWA 生成 HTML

### 6.3 PDF 下载

`DocMain.handleDownloadPdf(...)`
-> 写临时 HTML
-> `Html2PdfByChrome.convert2PDF(...)`
-> 输出 PDF 链接

### 6.4 子文档

`DocCreate.createOne()`
-> 读取子文档参数
-> 获取子模板
-> 递归生成子文档 HTML
-> 回填主模板

## 7. 使用与维护时要注意

1. 文档模板、参数和业务数据是联动的，改模板字段前要一起检查参数表与 Java 替换逻辑。
2. `DOC_TYPE_EWA` 并不在本地模板里完全闭环，真实内容可能来自外部 EWA XML。
3. PDF 输出依赖 Chrome headless，部署环境要满足转换条件。
4. 子文档是递归生成的，复杂模板排查时要同时关注父模板和子模板。
5. `DocUtils` 里的特殊占位符替换可能影响最终输出，遇到文档里出现“额外片段”时要先检查这里。

## 8. 总结

可以把 `doc` 模块理解为：

**一个基于模板、参数表和业务数据的文档渲染与输出系统**

其中：

- `DocMain` 管入口与输出方式
- `DocPage` 管页面层文档内容
- `DocCreate` 管模板解析与 HTML 生成
- `DocTmp` / `DocValMain` 等类管模板与值数据
- `DocUtils` 管特殊替换与业务辅助

如果后续扩展文档能力，优先沿用现有模式：模板驱动、参数表驱动、必要时复用 `HtmlControl` 的 EWA 文档生成能力。
