---
name: http-warn-curl
description: Add WARN logging with curl command when HTTP response status code is not 200 in SDK/API client classes using UNet
source: auto-skill
extracted_at: '2026-06-29T11:49:09.655Z'
---

# HTTP 非 200 响应 — WARN 日志 + curl 命令

## 场景

在 API SDK / 客户端类（如 `ClientSdk`、`ServerSdk`）中，使用 `UNet` 发起 HTTP 请求后，当服务端返回非 200 状态码时，输出 WARN 日志（含状态码、方法、URL）以及一条可直接在终端执行的 curl 命令，方便快速排查问题。

## 实现步骤

### 1. 添加 Logger

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

private static final Logger LOGGER = LoggerFactory.getLogger(YourClass.class);
```

### 2. 添加 `logNon200Warning` 辅助方法

```java
/**
 * 当 HTTP 状态码不为 200 时，输出 WARN 日志和对应的 curl 命令
 *
 * @param net    UNet 实例
 * @param method HTTP 方法 (GET/POST/PUT/DELETE)
 * @param url    请求 URL
 * @param body   请求体（可为 null）
 */
private void logNon200Warning(UNet net, String method, String url, String body) {
    int statusCode = net.getLastStatusCode();
    if (statusCode == 200) {
        return;
    }
    LOGGER.warn("HTTP status code {} for {} {}", statusCode, method, url);

    StringBuilder curl = new StringBuilder();
    curl.append("curl -X ").append(method).append(" '").append(url).append("'");

    // 根据实际情况调整 Authorization header 格式
    if (StringUtils.isNotBlank(this.tokenField)) {
        curl.append(" -H 'Authorization: ").append(this.tokenField).append("'");
    }
    // Bearer token 场景
    // curl.append(" -H 'Authorization: Bearer ").append(this.serverToken).append("'");

    if (StringUtils.isNotBlank(body)) {
        // 转义 body 中的单引号，安全拼接
        String escaped = body.replace("'", "'\\''");
        curl.append(" -d '").append(escaped).append("'");
    }

    LOGGER.warn("Curl: {}", curl);
}
```

### 3. 在每个 HTTP 请求后调用

**模板代码** — 在 `net.doGet/doPost/doPut/doDelete/postMsg` 之后紧接着调用：

```java
// GET 请求 - 无 body
UNet net = this.createNet();
String rst = net.doGet(url);
this.logNon200Warning(net, "GET", url, null);

// POST 请求 - 有 body
UNet net = this.createNet();
String rst = net.doPost(url, bodyStr);
this.logNon200Warning(net, "POST", url, bodyStr);

// PUT 请求 - 有 body
UNet net = this.createNet();
String rst = net.doPut(url, bodyStr);
this.logNon200Warning(net, "PUT", url, bodyStr);

// DELETE 请求 - 无 body
UNet net = this.createNet();
String rst = net.doDelete(url);
this.logNon200Warning(net, "DELETE", url, null);

// DELETE 请求 - 有 body
UNet net = this.createNet();
String rst = net.doDelete(url, bodyContent);
this.logNon200Warning(net, "DELETE", url, bodyContent);

// postMsg 是 doPost 的别名
UNet net = this.createNet();
String rst = net.postMsg(url, bodyContent);
this.logNon200Warning(net, "POST", url, bodyContent);
```

### 4. 注意事项

- **body 变量名冲突**：如果方法内已有同名的 `JSONObject body` 变量，提取字符串时请改用不同变量名（如 `bodyStr`）
- **Authorization 格式**：根据 `UNet` 设置的 header 格式来构建 curl 的 `-H` 参数
- **空 body**：当请求没有 body 时传入 `null`，辅助方法内部会跳过 `-d`
- **单引号转义**：body 内容中的单引号用 `'\''` 转义，确保 bash 下安全执行
- **仅非 200 触发**：状态码为 200 时直接 return 不输出任何内容，避免正常请求无意义刷屏
