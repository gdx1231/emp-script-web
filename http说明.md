# http 说明

## 1. 模块定位

`http` 模块用于提供一组轻量级 HTTP 扩展入口，把 Servlet 请求包装成仓库内部业务能力的统一访问接口。

它不是 MVC 控制器框架，而是一组：

- 小型入口类
- 基于 `IHttp`
- 使用 `RequestValue`
- 直接返回字符串或写响应流

的适配层。

## 2. 核心接口

### `IHttp`

`IHttp` 是整个模块的统一入口接口：

```java
String response(HttpServletRequest request, HttpServletResponse response) throws Exception;
```

它约定：

- 输入是标准 Servlet 请求 / 响应
- 输出通常是：
  - JSON 字符串
  - HTML 字符串
  - 或者直接写入 `response` 后返回 `null`

这也是 `http` 模块所有实现类的统一风格。

## 3. 主要实现类

### 3.1 `HttpUploadResource`

用于对外读取上传目录中的资源。

主要流程：

1. 根据请求 URL 解析资源路径
2. 调用 `UploadResources.getResource(...)`
3. 根据 `UploadResource` 的状态、MIME 类型和二进制标志输出内容
4. 可设置缓存头

适合把上传目录中的图片、样式或文本资源安全对外暴露。

### 3.2 `HttpShortUrl`

用于生成短网址。

输入参数：

- `u`：原始长网址

逻辑：

- 读取当前 `g_adm_id` 与 `g_sup_id`
- 调用 `ShortUrl.addUrl(...)`
- 返回 JSON，包含短码与完整记录

是一个典型的“非常薄的 HTTP 包装器”。

### 3.3 `HttpShortUrlVerify`

用于根据短码跳转到原始网址。

输入参数：

- `uid`

逻辑：

- 查询短码记录
- 若存在则 `sendRedirect(...)`
- 若不存在则返回 404

是短网址访问入口。

### 3.4 `HttpQRCode`

用于生成二维码输出。

支持参数：

- `msg`：二维码内容
- `width`：宽高
- `logo`：二维码中间 logo
- `show`：输出模式

输出模式包括：

- `base64`
- `url`
- 默认图片二进制

同时它还支持：

- 文件缓存
- `ETag`
- `Cache-Control`
- logo 路径安全检查

### 3.5 `HttpFileViewBase`

这是文件查看 HTTP 能力的公共基类，不直接实现 `IHttp`，但为文件查看相关入口提供统一输出逻辑。

主要能力：

- 下载 / 在线查看 / 缩略图
- 文档转 PDF
- 图片预览
- PDF、视频、音频查看
- 缓存头处理
- 统一错误页面

这个类是文件预览体系的核心基础设施。

### 3.6 `HttpOaFileView`

面向 `OA_FILE` 表的文件查看入口。

### 3.7 `HttpOaSysAttView`

面向 `SYS_ATTS` 表的附件查看入口。

这两类入口已经在 `fileviewer说明.md` 中有更详细说明。

## 4. 模块设计风格

`http` 模块有非常鲜明的统一风格：

### 4.1 入口层保持很薄

通常每个实现类只做这些事：

1. 用 `RequestValue` 包装请求
2. 校验关键参数
3. 调用业务类
4. 返回字符串或写响应

复杂逻辑通常下沉到：

- `shortUrl`
- `qrcode`
- `uploadResources`
- `fileViewer`

等模块。

### 4.2 以 `RequestValue` 作为上下文

几乎所有入口都第一时间创建：

```java
RequestValue rv = new RequestValue(request);
```

然后通过它读取：

- 请求参数
- 登录态参数
- 商户参数

这与仓库其他模块完全一致。

### 4.3 允许返回字符串，也允许直接写流

例如：

- JSON 接口通常返回字符串
- 图片、文件、资源下载通常直接写 `response`

因此实现类既可以当“返回内容生成器”，也可以当“直接输出器”。

## 5. 典型调用链

### 5.1 上传资源

`HttpUploadResource`
-> `UploadResources`
-> `UploadResource`

### 5.2 短网址

`HttpShortUrl`
-> `ShortUrl`
-> `UrlShort`

### 5.3 短网址跳转

`HttpShortUrlVerify`
-> `ShortUrl.getUrlShort(uid)`
-> redirect

### 5.4 二维码

`HttpQRCode`
-> `QRCode`
-> 文件缓存 / 输出

### 5.5 文件预览

`HttpOaFileView` / `HttpOaSysAttView`
-> `HttpFileViewBase`
-> 文件查看逻辑

## 6. 适用场景

适合放在 `http` 模块里的能力通常满足：

- 参数简单
- 逻辑边界明确
- 输出结果直接
- 已有成熟业务类可复用

不适合放在这里的通常是：

- 大型业务编排
- 复杂事务逻辑
- 重度状态管理流程

因为 `http` 模块的定位本来就是轻适配层。

## 7. 使用与维护时要注意

1. 新的 HTTP 入口优先实现 `IHttp`，保持与现有模块一致。
2. 入口类应尽量薄，把复杂逻辑下沉到领域或工具模块。
3. 若入口需要权限控制，优先复用现有 `RequestValue` / 登录态约定，而不是新增一套认证机制。
4. 文件、资源、二维码一类入口通常涉及缓存与安全校验，改动时要连同响应头和路径校验一起考虑。
5. 如果一个功能已经在 `fileViewer`、`uploadResources`、`shortUrl`、`qrcode` 中有成熟实现，不要在 `http` 里重复造逻辑。

## 8. 总结

可以把 `http` 模块理解为：

**面向 Servlet 的轻量业务入口层**

其中：

- `IHttp` 统一入口约定
- `HttpUploadResource` 负责上传资源访问
- `HttpShortUrl` / `HttpShortUrlVerify` 负责短网址生成与跳转
- `HttpQRCode` 负责二维码输出
- `HttpFileViewBase` 及其子类负责文件预览与下载

如果后续新增 HTTP 扩展入口，建议继续遵循现有模式：薄入口、`RequestValue` 统一上下文、复用已有业务模块。
