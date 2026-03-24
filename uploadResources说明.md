# uploadResources 说明

## 1. 模块定位

`uploadResources` 模块用于从上传目录中安全读取资源，并提供：

- 资源路径校验
- 文本 / 二进制识别
- 内容读取
- 资源缓存

它本质上是“上传目录静态资源访问层”，通常与 `HttpUploadResource` 配套使用。

## 2. 关键类

### 2.1 `UploadResources`

这是资源加载主类。

主要职责：

- 规范化请求路径
- 查询缓存
- 做路径与扩展名安全校验
- 从上传目录读取资源
- 按内容类型返回 `UploadResource`

### 2.2 `UploadResource`

这是资源返回对象。

封装内容包括：

- `path`
- `type`
- `content`
- `buffer`
- `status`
- `binary`

也就是说，调用方无需关心底层文件读取细节，只需要根据 `binary` 决定输出文本还是字节流。

### 2.3 `UploadResourcesCached`

上传资源缓存层。

基于 Guava Cache 实现，特点：

- 最大缓存数：100
- 最近访问后 10 分钟过期
- 只缓存成功读取的资源

它用于降低热点上传资源的重复磁盘读取成本。

## 3. 工作方式

## 3.1 资源入口

`UploadResources.getResource(resourcePath)` 的流程是：

1. 规范化路径
2. 查缓存
3. 如未命中则执行 `loadResource(...)`
4. 成功读取后放入缓存

## 3.2 物理路径

真实文件目录来自：

- `UPath.getPATH_UPLOAD()`

因此该模块完全依赖系统上传目录配置，而不是仓库内资源目录。

## 3.3 文本与二进制识别

模块会根据扩展名和 MIME 类型判断内容是否为二进制。

明确按文本处理的扩展名包括：

- `htm`
- `html`
- `txt`
- `csv`
- `json`
- `css`
- `xml`

其它类型则根据 `FileOut.MAP` 推断是否是 `text/*`。

## 4. 安全限制

`UploadResources` 有较明确的安全防护。

## 4.1 禁止路径穿越

如果路径包含：

- `..`

则直接拒绝。

## 4.2 禁止目录或无扩展名

如果：

- 扩展名为空
- 看起来像目录

则拒绝读取。

## 4.3 禁止敏感 / 可执行文件

明确拒绝的扩展名包括：

- `exe`
- `bat`
- `cmd`
- `sh`
- `dmg`
- `java`
- `jsp`
- `class`
- `jar`
- `properties`
- `js`

这说明该模块并不是一个任意文件下载器，而是严格限制可暴露资源范围。

## 4.4 禁止敏感配置文件

文件名中若包含：

- `ewa_conf`
- `appliaction.yml`

也会直接拒绝。

## 5. 状态码语义

从代码可见，常见状态包括：

- `200`：成功
- `403`：空扩展名或目录
- `404`：文件不存在
- `500`：非法扩展名或读取异常
- `501`：敏感配置文件
- `502`：非法路径（如 `..`）

这让调用方可以根据状态决定 HTTP 响应。

## 6. 缓存机制

`UploadResourcesCached` 只缓存读取成功的资源。

关键策略：

- `maximumSize(100)`
- `expireAfterAccess(10, TimeUnit.MINUTES)`

这说明缓存更偏向“热点上传资源短期加速”，而不是长期资源托管。

## 7. 与 HTTP 层的关系

`uploadResources` 模块本身不处理 HTTP。

它通常与：

- `com.gdxsoft.web.http.HttpUploadResource`

配套使用。

调用关系通常是：

`HttpUploadResource.response(...)`
-> `UploadResources.getResource(path)`
-> 根据 `UploadResource` 输出文本或字节流

## 8. 使用与维护时要注意

1. 该模块读的是上传目录，不是 classpath 资源。
2. 安全限制较严格，某些扩展名即使物理存在也不会被对外暴露。
3. `js` 被显式禁止，这意味着上传目录不适合作为任意前端脚本托管目录。
4. 如果出现资源访问不到，排查顺序应是：路径 -> 扩展名 -> 敏感文件规则 -> 上传目录配置 -> 文件是否存在。
5. 缓存只缓存成功资源，修改上传文件内容后要考虑缓存生效周期。

## 9. 总结

可以把 `uploadResources` 模块理解为：

**一个带安全限制和缓存能力的上传目录资源读取层**

其中：

- `UploadResources` 负责读取与校验
- `UploadResource` 负责返回结构
- `UploadResourcesCached` 负责热点缓存

如果后续扩展上传资源能力，优先保持现有模式：先通过白名单 / 安全规则过滤，再统一输出资源，而不要绕过该模块直接暴露上传目录。
