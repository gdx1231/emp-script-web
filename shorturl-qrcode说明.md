# shortUrl / qrcode 说明

## 1. 模块定位

`shortUrl` 与 `qrcode` 两个模块经常一起使用：

- `shortUrl` 负责把长链接缩成短码
- `qrcode` 负责把消息或 URL 生成二维码图片

在当前仓库里，它们不仅能单独用，也能组合成“短链接二维码”能力。

## 2. `shortUrl` 模块

### 2.1 核心类

#### `ShortUrl`

这是短地址业务主类。

主要职责：

- 根据 `uid` 查询短地址
- 根据原始 URL 查询已有短地址
- 创建新的短地址记录
- 生成唯一短码

#### `UrlShort`

这是 `url_short` 表的映射类。

关键字段包括：

- `url_id`
- `url_uid`
- `url_full`
- `url_md5`
- `url_status`
- `url_cdate`
- `url_vdate`
- `sup_id`
- `adm_id`

说明短地址不只是保存长链接，也带有商户和管理员上下文。

### 2.2 工作方式

创建短地址时，`ShortUrl.addUrl(...)` 会：

1. 生成 `url_uid`
2. 保存原始 URL
3. 计算 `url_md5`
4. 记录 `sup_id`、`adm_id`
5. 把状态标成 `USED`

查询时，`ShortUrl.getUrlShort(uid)` 只会读取：

- `URL_STATUS='USED'`

的记录。

### 2.3 唯一短码生成

`ShortUrl.createUid(...)` 的思路不是单次随机碰运气，而是：

1. 一次性生成大量候选码
2. 长度从 4 位开始，逐步增加到 10 位
3. 批量查询数据库里哪些已经存在
4. 返回第一个没占用的值

这让它在短码较多时仍有较高成功率。

### 2.4 入口关系

`http` 模块中的：

- `HttpShortUrl`
- `HttpShortUrlVerify`

分别负责：

- 创建短地址
- 根据 `uid` 重定向

## 3. `qrcode` 模块

### 3.1 核心类

#### `QRCode`

这是二维码底层工具类。

主要职责：

- 生成二维码 `BufferedImage`
- 输出 JPEG / GIF
- 支持附加 logo
- 生成 Web 可访问缓存文件路径

它基于 ZXing 生成二维码，并会裁掉外围多余白边。

#### `QRCodeWeb`

这是面向 Web 场景的二维码业务封装。

主要职责：

- 校验 logo 路径
- 根据参数生成 hash
- 使用 `If-None-Match` / `ETag` 做缓存协商
- 生成二维码缓存文件
- 按需先创建短地址，再生成二维码

也就是说，`QRCodeWeb` 是“二维码页面场景服务层”，而 `QRCode` 是底层图像工具。

## 4. 短链二维码组合方式

`QRCodeWeb.createQRCode(...)` 支持：

- `useShortUrl`
- `onlyNumber`

当 `useShortUrl` 为真时，流程会变成：

1. 先创建短地址
2. 拿到短地址 URL
3. 再用短地址生成二维码

这样做的好处是：

- 二维码内容更短
- 真正指向的长链接可以被收口到 `url_short`

## 5. 缓存与文件路径

二维码图片不是每次都重新生成。

### 5.1 物理缓存

`QRCode.getQRCodeSavedPath(...)` 会根据 hash 生成：

- 物理缓存路径
- 对应 URL

目录位于：

- `UPath.getPATH_IMG_CACHE()`

### 5.2 HTTP 缓存

`QRCodeWeb` 和 `HttpQRCode` 都支持：

- `ETag`
- `If-None-Match`
- `Cache-Control`

因此同一个二维码请求在浏览器侧和文件侧都有缓存能力。

## 6. logo 安全限制

无论是 `QRCodeWeb` 还是 `HttpQRCode`，都对 logo 路径有较严格限制。

明确拒绝：

- `./`
- `.\`
- `..`
- `file://`

并且 logo 必须位于上传目录下。

这说明二维码中心 logo 不是任意 URL，而是受控本地文件。

## 7. 使用与维护时要注意

1. `shortUrl` 当前创建逻辑是直接新建记录，不会先按 URL 去重复用旧记录。
2. `ShortUrl.getUrlShort(uid)` 只认 `URL_STATUS='USED'`，状态字段会直接影响可访问性。
3. 短码生成是“批量候选 + 查重”策略，不要轻易改成简单单次随机。
4. 二维码缓存依赖文件系统路径配置，部署环境要确保缓存目录可写。
5. logo 必须来自上传目录，不能直接传互联网 URL。
6. `QRCodeWeb` 与 `HttpQRCode` 都有自己的 Web 输出逻辑，扩展时要先看复用哪一层更合适。

## 8. 总结

可以把这两块理解为：

**短地址收口层 + 二维码输出层**

其中：

- `ShortUrl` / `UrlShort` 管短地址创建与查询
- `QRCode` 管底层二维码图像生成
- `QRCodeWeb` 管 Web 场景缓存、logo 与短链组合
- `HttpShortUrl` / `HttpShortUrlVerify` / `HttpQRCode` 管最终 HTTP 暴露

如果后续扩展分享能力，优先沿用“先短链、后二维码、再走缓存”的现有模式。
