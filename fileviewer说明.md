# fileViewer 说明

## 1. 模块定位

`fileViewer` 相关代码用于处理文件的**在线预览、缩略图输出、二进制直出、下载控制以及文档转 PDF/HTML 预览**。

这一套能力并不只服务某一张表，而是提供了三类常见用法：

- 基于 `OA_FILE` 表的文件查看：`HttpOaFileView`
- 基于 `SYS_ATTS` 表的附件查看：`HttpOaSysAttView`
- 基于外部 XML 配置映射任意业务表的通用文件查看：`com.gdxsoft.web.fileViewer.OAFileView`

它们的公共能力都来自基类：

- `com.gdxsoft.web.http.HttpFileViewBase`

## 2. 相关类说明

### 2.1 `HttpFileViewBase`

这是文件查看模块的核心基类，负责以下通用能力：

- 识别请求模式：普通查看、`inline`、`download`、`SMALL` / `SMAILL`
- 根据文件类型选择输出方式：文档、图片、PDF、视频、音频、其他二进制
- 处理缓存头：`ETag`、`Cache-Control`
- 图片缩略图与尺寸缩放：`resize`
- 文档转 PDF 后预览
- 生成图片、音频、视频、PDF 的默认 HTML 页面

它决定了一个文件最终以什么方式展示或输出。

### 2.2 `HttpOaFileView`

面向 `OA_FILE` 表的专用查看器。

主要逻辑：

- 根据 `OAF_ID`、`PDF_OAF_ID` 或 `unid` 查询 `OA_FILE`
- 从 `OAF_URL` 解析物理文件路径
- 根据 `OAF_IS_DL` 判断是否允许下载
- 调用 `HttpFileViewBase.handleFile(...)` 输出内容

适合已有 `OA_FILE` 表结构的业务系统直接使用。

### 2.3 `HttpOaSysAttView`

面向 `SYS_ATTS` 表的专用附件查看器。

主要逻辑：

- 根据 `file_id` 或 `File_UnId` 查询 `sys_atts`
- 支持通过 `db` 参数切换数据库名
- 对非公开文件校验当前登录商户 `sup_id`
- 物理文件通过 `file_path` 与上传目录拼接定位
- 总是允许下载，但会先做权限检查

适合系统级附件、公共上传文件一类的访问场景。

### 2.4 `OAFileView`

这是最灵活的通用查看器，位于：

- `com.gdxsoft.web.fileViewer.OAFileView`

它通过一个外部 XML 配置文件，把“业务表字段”和“文件查看规则”绑定起来，从而支持任意表的文件查看。

它的能力包括：

- 通过 `tb` 参数决定使用哪一组表配置
- 按配置拼接 SQL 查询记录
- 按配置字段定位文件路径、扩展名、标题、MD5、缓存时间
- 自动判断是否需要按 `sup_id` 做权限限制
- 支持按上传目录或指定物理目录读取文件

如果你的项目文件并不存放在 `OA_FILE` / `SYS_ATTS` 中，而是分散在业务表里，通常应使用这一类。

### 2.5 `BinToPhy`

`BinToPhy` 是一个辅助类，用于把数据库中的二进制文件字段批量导出成物理文件，并回写路径、扩展名、长度、MD5 等字段。

它的主要用途不是在线查看，而是做一次性的“二进制字段转物理文件”处理，便于后续由 `OAFileView` 直接读取物理文件。

## 3. 请求参数与输出模式

`HttpFileViewBase` 会在 `initParameters()` 中解析这些通用参数：

### 3.1 通用参数

- `download` / `download_file`
  - 进入下载模式
- `inline`
  - 不生成预览 HTML，直接以内联二进制方式输出
- `SMALL` / `SMAILL`
  - 输出缩略图
- `resize`
  - 对图片按指定尺寸缩放
- `EWA_APP` / `EWA_AJAX`
  - 控制是否跳过完整 HTML 头尾

### 3.2 输出行为

当进入普通查看模式时，基类会按扩展名决定输出方式：

- 文档：转 PDF 后预览
- 图片：输出图片查看页面，支持缩放、旋转、打印、下载
- PDF：优先用浏览器原生 PDF 预览，否则退到 `pdf.js`
- 视频：输出 `<video>`
- 音频：输出 `<audio>`
- 其他文件：如允许下载则直接下载

### 3.3 缩略图行为

当带 `SMALL` / `SMAILL` 参数时：

- 如果是图片，则生成 128x128 缩略图并输出
- 如果不是图片，则跳转到对应文件类型图标

## 4. `OAFileView` 的 XML 配置机制

`OAFileView` 的核心并不是写死表结构，而是依赖一个 XML 配置文件。

构造函数：

```java
new OAFileView(pdfJs, tableBinXmlFilePath)
```

其中：

- `pdfJs`：pdf.js 的查看器地址，例如 `/xxx/pdfjs/web/viewer.html`
- `tableBinXmlFilePath`：描述表字段映射关系的 XML 文件路径

`BinToPhy` / `OAFileView` 会读取 XML 中的 `<table>` 节点。根据代码可知，常用属性包括：

- `id`：外部请求中 `tb` 参数对应的标识
- `name`：数据库表名
- `keyf`：主键字段，可多个，用逗号分隔
- `filef`：物理文件路径字段
- `extf`：扩展名字段
- `titlef`：显示文件名字段
- `md5f`：MD5 字段
- `supf`：商户字段，用于 `g_sup_id` 权限校验，可多个
- `cached`：缓存时长（秒）
- `phy_root`：固定物理目录前缀

如果还需要支持二进制转物理文件，`BinToPhy` 还会使用：

- `binf`：二进制字段
- `lenf`：文件长度字段

下面这个示例是根据代码推导出的典型配置形式：

```xml
<tables>
  <table
      id="oa_req_att"
      name="OA_REQ_ATT"
      keyf="ATT_ID"
      filef="ATT_URL"
      extf="ATT_EXT"
      titlef="ATT_NAME"
      md5f="ATT_MD5"
      supf="SUP_ID"
      cached="300" />
</tables>
```

请求时传入：

```text
?tb=oa_req_att&ATT_ID=1001
```

即可按该配置查出记录，再进入统一的文件输出流程。

## 5. `OAFileView` 的请求流程

`OAFileView.response(...)` 的处理顺序大致如下：

1. 从请求中读取 `tb`
2. 根据 `tb` 在 XML 中找到对应 `<table>` 配置
3. 判断是否需要登录商户系统
4. 按配置拼接 SQL 查询业务记录
5. 从记录中取出物理文件路径、扩展名、标题、MD5、缓存时间
6. 定位物理文件
7. 调用 `handleFile(...)` 执行预览、下载或缩略图输出

其中有几个额外细节：

- 如果配置了 `phy_root`，则按 `phy_root + path` 拼接物理路径
- 如果未配置 `phy_root`，会优先尝试上传目录相关路径
- 如果带了 `download_name`，可覆盖默认下载文件名
- 如果请求里带 `resize`，图片输出时会参与 MD5/缓存计算

## 6. 权限控制逻辑

### 6.1 `HttpOaFileView`

主要基于 `OA_FILE` 表中的 `SUP_ID`、`OAF_UNID`、`OAF_IS_DL` 等字段控制。

其中：

- 通过 `unid` 查询时，代码里没有再强制校验商户登录
- 通过 `OAF_ID` 查询普通查看时，会附带 `SUP_ID = @G_SUP_ID`
- 下载权限由 `OAF_IS_DL` 控制

### 6.2 `HttpOaSysAttView`

权限逻辑更明确：

- `file_para1 = COM_YES` 视为公开文件
- 非公开文件必须先登录商户系统
- 且登录商户的 `sup_id` 必须与附件记录中的 `sup_id` 一致

### 6.3 `OAFileView`

通用查看器默认也会做商户权限校验：

- 如果 XML 配置里存在 `supf`
- 且当前表不在跳过列表中
- 且没有显式 `skipSupCheck`

则 SQL 会自动附加：

```sql
sup_field = @g_sup_id
```

若未登录商户系统：

- 普通查看会重定向到商户登录页面
- 缩略图请求会跳转到默认占位图

另外，代码里还内置了一些跳过校验的特例，例如：

- `lbs_photos`
- 某些 `sys_atts` 投资人报告场景
- 某些来源于 `/app-2017/` 的请求

## 7. 文件路径解析逻辑

文件定位的关键依赖是：

- `UPath.getPATH_UPLOAD()`
- `UPath.getPATH_UPLOAD_URL()`

常见情况有两种：

### 7.1 路径字段里已经是上传 URL

例如字段中存的是类似上传虚拟路径，代码会把它替换成真实上传目录路径再访问物理文件。

### 7.2 路径字段里是相对路径

代码会直接拼到上传目录后面。

### 7.3 XML 配置中显式给了 `phy_root`

此时优先按：

```text
phy_root + path
```

去定位文件。

## 8. 文档与 PDF 预览

文档预览能力主要在 `HttpFileViewBase` 中实现。

默认处理方式是：

- 对 doc / docx / xls / xlsx / ppt / pptx / wps / odt / ods / odp / txt / html 等文档
- 优先转成 `.pdf`
- 然后走 PDF 预览页面

相关转换类：

- `File2Pdf`
- `File2Html`

其中：

- `viewDocumentAsPdf(...)`：文档转 PDF 后再预览
- `viewDocument(...)`：文档转 HTML 后嵌入页面（代码中目前主要走 PDF 方案）

PDF 预览时需要传入 `pdfJs` 地址，否则浏览器不支持原生 PDF 预览时无法回退到 `pdf.js`。

## 9. 图片查看能力

图片查看页是这一套里交互最完整的部分，支持：

- 放大
- 缩小
- 左旋
- 右旋
- 下载
- 打印
- 鼠标滚轮缩放
- 拖拽移动

如果只想直接输出图片二进制，可使用 `inline=1`。

如果只想拿缩略图，可使用 `SMALL=1` 或兼容拼写 `SMAILL=1`。

## 10. `BinToPhy` 的用途

`BinToPhy` 适用于如下场景：

- 某张业务表原本把文件内容存在 BLOB / 二进制字段中
- 现在希望迁移为“物理文件 + 数据库存路径”
- 以便后续直接接入 `OAFileView`

它会：

1. 读取 XML 配置中定义的表结构映射
2. 每次取 10 条尚未生成物理文件的记录
3. 从二进制字段生成文件
4. 自动识别或修正扩展名
5. 计算 MD5、长度
6. 回写物理路径、扩展名、长度、MD5

输出目录默认形如：

```text
/business/{table_name_lower}/...
```

实际物理根目录则通过 `UPath.getPATH_UPLOAD()` 决定。

## 11. 接入建议

如果你的场景是：

### 11.1 系统已经有 `OA_FILE`

优先使用：

- `HttpOaFileView`

### 11.2 系统已经有 `SYS_ATTS`

优先使用：

- `HttpOaSysAttView`

### 11.3 文件散落在自定义业务表

优先使用：

- `OAFileView`

并准备一份 `<table>` 映射 XML。

### 11.4 数据库存的是二进制字段

先用：

- `BinToPhy`

把数据整理成物理文件模式，再接 `OAFileView`。

## 12. 使用时需要特别注意

1. `OAFileView` 强依赖外部 XML 配置，没有配置就无法工作。
2. `pdfJs` 不是可选体验项，而是 PDF 预览兜底能力的一部分。
3. `SMALL` 与 `SMAILL` 两种拼写都被兼容，接入时尽量统一使用 `SMALL`。
4. 文档预览不是直接读源码文件，而是依赖转换后的 `.pdf` 或 `.html` 中间产物。
5. 权限控制默认偏严格，尤其是通用 `OAFileView` 会自动带上 `g_sup_id` 校验。

## 13. 总结

可以把 `fileViewer` 理解为：

**一个面向多种业务表的统一文件查看与下载框架**

其中：

- `HttpFileViewBase` 负责统一输出能力
- `HttpOaFileView` / `HttpOaSysAttView` 负责面向固定表结构的快捷接入
- `OAFileView` 负责面向任意业务表的配置化接入
- `BinToPhy` 负责把二进制存储迁移到物理文件存储

如果后续需要扩展新的文件表，通常优先考虑“补 XML 配置 + 使用 `OAFileView`”，而不是重复造一个新的文件查看实现。
