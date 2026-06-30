---
name: java-http-curl-log
description: Add HTTP non-200 warning logging with curl commands in Java HTTP client classes — helper method, per-call invocation, header/body inclusion
source: auto-skill
extracted_at: '2026-06-29T11:47:56.555Z'
---

# Java HTTP 非 200 响应 WARN 日志 + Curl 命令输出

## 场景

Java HTTP 客户端类中，每个 HTTP 请求方法（GET/POST/PUT/DELETE）调用后都需要在状态码 != 200 时输出 WARN 日志，并附带一条可直接在终端执行的 curl 命令，便于调试。

## 步骤

### 1. 添加 Logger

在类中引入 slf4j Logger：

```java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 作为类字段
private static final Logger LOGGER = LoggerFactory.getLogger(ClientSdk.class);
```

### 2. 创建辅助方法

该方法接收 `net`（或类似的 HTTP 客户端对象）、HTTP 方法、URL 和请求体，在状态码非 200 时输出警告和 curl 命令：

```java
/**
 * 当 HTTP 状态码不为 200 时，输出 WARN 日志和对应的 curl 命令
 */
private void logNon200Warning(UNet net, String method, String url, String body) {
    int statusCode = net.getLastStatusCode();
    if (statusCode == 200) {
        return;
    }
    LOGGER.warn("HTTP status code {} for {} {}", statusCode, method, url);

    StringBuilder curl = new StringBuilder();
    curl.append("curl -X ").append(method).append(" '").append(url).append("'");

    // 添加认证头（根据实际情况调整）
    if (StringUtils.isNotBlank(this.userToken)) {
        curl.append(" -H 'Authorization: ").append(this.userToken).append("'");
    }
    // 添加其他已知头部（根据实际情况调整）
    if (StringUtils.isNotBlank(this.fromIp)) {
        curl.append(" -H 'X-Forwarded-For: ").append(fromIp).append("'");
    }

    // 添加请求体（带单引号转义，防止 body 中含 ' 破坏 shell 语法）
    if (StringUtils.isNotBlank(body)) {
        String escaped = body.replace("'", "'\\''");
        curl.append(" -d '").append(escaped).append("'");
    }

    LOGGER.warn("Curl: {}", curl);
}
```

**关键细节：**
- 状态码检查在方法最前，200 时快速返回，避免日志开销
- curl 命令的 URL 用单引号包裹，防止特殊字符（`&`, `?`, `$` 等）被 shell 解释
- body 中的单引号用 `'\''` 转义，这是 bash 单引号字符串中插入单引号的标准手法
- 只打印非空非空的已知头部，避免输出多余的 `-H` 参数

### 3. 在每个 HTTP 请求方法后调用

在每个方法中执行 HTTP 请求后，马上调用辅助方法：

```java
// GET 无 body
UNet net = this.createNet();
String rst = net.doGet(url);
this.logNon200Warning(net, "GET", url, null);

// POST/PUT 有 body
String body = msg.getJSONObject("body").toString();
UNet net = this.createNet();
String rst = net.doPost(url, body);
this.logNon200Warning(net, "POST", url, body);

// DELETE 无 body
String rst = net.doDelete(url);
this.logNon200Warning(net, "DELETE", url, null);

// DELETE 有 body
String rst = net.doDelete(url, bodyContent);
this.logNon200Warning(net, "DELETE", url, bodyContent);
```

### 4. 涉及 body 的方法中提取 body 变量

如果 body 原本是内联传入的（如 `net.doPost(url, msg.optJSONObject("body").toString())`），需要先提取为局部变量，使同一 body 对象可用于日志：

```java
// 改前
String rst = net.doPost(url, msg.optJSONObject("body").toString());

// 改后
String body = msg.optJSONObject("body").toString();
String rst = net.doPost(url, body);
this.logNon200Warning(net, "POST", url, body);
```

## 注意事项

- 如果 HTTP 客户端类无法获取请求体（body 在内部被序列化），则只传 `null`，curl 命令中不包含 `-d` 参数
- 避免在方法中多次获取 body（如先取一次做日志，再取一次传参），应提取为局部变量
- 如果 HTTP 客户端对象（如 `UNet`）的 `getLastStatusCode()` 返回 -1 或 0（请求未执行），方法应安全返回
- Token/敏感信息会打印到日志中 — 确保生产环境日志级别不要设置为 DEBUG/WARN 全开，或考虑脱敏
