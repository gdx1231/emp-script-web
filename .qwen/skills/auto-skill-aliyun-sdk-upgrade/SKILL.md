---
name: cloud-sdk-removal
description: Replace Aliyun and Tencent Cloud Java SDKs with pure HTTP implementations — TC3-HMAC-SHA256 / HMAC-SHA1 signatures, UNet HTTP client, and per-API error detection patterns
source: auto-skill
extracted_at: '2026-06-27T09:45:00.000Z'
---

# 云 SDK 去依赖化：纯 HTTP 替代模式

将阿里云、腾讯云 Java SDK 替换为 `UNet` + 签名算法的标准化流程。适用于 OpenAPI 签名服务（非 OSS 等二进制上传场景）。

## 目录

1. [阿里云 HMAC-SHA1 签名](#阿里云-hmac-sha1)
2. [腾讯云 TC3-HMAC-SHA256 签名](#腾讯云-tc3-hmac-sha256)
3. [通用测试模式](#通用测试模式)
4. [迁移检查清单](#迁移检查清单)

## 阿里云 HMAC-SHA1

适用于阿里云 OpenAPI 服务（短信 Dysmsapi、DNS Alidns 等），签名版本 1.0。

### 核心架构

每个 API 类含：
- `buildCommonParams()` → TreeMap（自动字母序）
- `buildQueryString(TreeMap)` → 百分号编码拼接
- `hmacSha1(data, key)` → 签名计算
- `doSignedPost(params)` → 签名 + UNet POST + API 错误检测

### API 错误检测（关键）

阿里云 API 返回**两种 JSON**：
- 成功：不含 `Code` 字段，直接是业务字段
- 失败：含 `Code` + `Message` 字段

```java
JSONObject json = new JSONObject(httpBody);
if (json.has("Code")) {
    throw new RuntimeException(json.optString("Code") + ": " + json.optString("Message"));
}
return json;
```

### 签名算法

```java
// 1. 参数按 key 字母序排序，构建 query string
String queryString = buildQueryString(new TreeMap<>(params));

// 2. 构造签名原文
String stringToSign = "POST" + "&" + percentEncode("/") + "&" + percentEncode(queryString);

// 3. HMAC-SHA1，key = AccessKeySecret + "&"
String signature = Base64.getEncoder().encodeToString(
    Mac.getInstance("HmacSHA1").doFinal(stringToSign.getBytes("UTF-8"))
);

// 4. 签名加入参数，POST
params.put("Signature", signature);
UNet net = new UNet();
String body = net.doPost(endpoint, new HashMap<>(params));
```

### percentEncode

```java
static String percentEncode(String value) throws UnsupportedEncodingException {
    return URLEncoder.encode(value, "UTF-8")
            .replace("+", "%20")    // 空格 → %20
            .replace("*", "%2A")    // 星号
            .replace("%7E", "~");   // 波浪号回退
}
```

### 端点与版本

| 服务 | Endpoint | API Version |
|---|---|---|
| 短信 | `https://dysmsapi.aliyuncs.com/` | `2017-05-25` |
| DNS | `https://alidns.aliyuncs.com/` | `2015-01-09` |

---

## 腾讯云 TC3-HMAC-SHA256

适用于腾讯云 API v3（短信 SMS、CVM 等），签名版本 TC3。

### 签名流程（4 步）

```java
// Step 1: Canonical Request
String canonicalHeaders = "content-type:application/json; charset=utf-8\n"
        + "host:" + HOST + "\n"
        + "x-tc-action:" + action.toLowerCase() + "\n";
String signedHeaders = "content-type;host;x-tc-action";
String hashedPayload = sha256Hex(payload);
String canonicalRequest = "POST" + "\n"
        + "/" + "\n"
        + "" + "\n"                  // canonical query string (empty for POST)
        + canonicalHeaders + "\n"
        + signedHeaders + "\n"
        + hashedPayload;

// Step 2: String to Sign
String credentialScope = date + "/" + service + "/tc3_request";
String hashedCanonicalRequest = sha256Hex(canonicalRequest);
String stringToSign = "TC3-HMAC-SHA256" + "\n"
        + timestamp + "\n"
        + credentialScope + "\n"
        + hashedCanonicalRequest;

// Step 3: Signature（层级 HMAC 派生）
byte[] secretDate = hmac256(("TC3" + secretKey).getBytes(UTF8), date);
byte[] secretService = hmac256(secretDate, service);
byte[] secretSigning = hmac256(secretService, "tc3_request");
String signature = bytesToHex(hmac256(secretSigning, stringToSign));

// Step 4: Authorization header
String authorization = "TC3-HMAC-SHA256 "
        + "Credential=" + secretId + "/" + credentialScope + ", "
        + "SignedHeaders=" + signedHeaders + ", "
        + "Signature=" + signature;
```

### HTTP 请求头

```java
UNet net = new UNet();
net.addHeader("Authorization", authorization);
net.addHeader("Content-Type", "application/json; charset=utf-8");
net.addHeader("Host", HOST);
net.addHeader("X-TC-Action", action);      // 如 "SendSms"
net.addHeader("X-TC-Timestamp", timestamp); // Unix 秒级时间戳
net.addHeader("X-TC-Version", version);    // 如 "2021-01-11"
net.addHeader("X-TC-Region", region);      // 如 "ap-beijing"
String responseBody = net.doPost(endpoint, payload);  // payload = JSON body string
```

### 响应格式（与阿里云不同）

腾讯云成功/失败都在 `Response` 包装内区分：

```json
// 成功
{"Response": {"RequestId": "...", "SendStatusSet": [...]}}

// 失败
{"Response": {"Error": {"Code": "...", "Message": "..."}, "RequestId": "..."}}
```

错误检测：
```java
JSONObject resp = new JSONObject(responseBody).optJSONObject("Response");
JSONObject error = resp.optJSONObject("Error");
if (error != null) {
    // 业务错误
}
```

### 加密工具

```java
private static byte[] hmac256(byte[] key, String msg) throws Exception {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(key, "HmacSHA256"));
    return mac.doFinal(msg.getBytes(StandardCharsets.UTF_8));
}

private static String sha256Hex(String s) throws Exception {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    return bytesToHex(md.digest(s.getBytes(StandardCharsets.UTF_8)));
}

private static String bytesToHex(byte[] bytes) {
    StringBuilder sb = new StringBuilder(bytes.length * 2);
    for (byte b : bytes) sb.append(String.format("%02x", b));
    return sb.toString();
}
```

### 端点与版本

| 服务 | Endpoint | API Version | Service | Region |
|---|---|---|---|---|
| 短信 | `https://sms.tencentcloudapi.com/` | `2021-01-11` | `sms` | `ap-beijing` |

---

## 通用测试模式

### 反射测试私有方法

签名相关方法通常为 `private` 或 `private static`，用反射测试：

```java
// static method
Method m = DomainAli.class.getDeclaredMethod("percentEncode", String.class);
m.setAccessible(true);
String result = (String) m.invoke(null, "hello world");

// instance method (如 SmsAliImpl.buildQueryString 是非 static）
Method m = SmsAliImpl.class.getDeclaredMethod("buildQueryString", TreeMap.class);
m.setAccessible(true);
String result = (String) m.invoke(instance, params);
```

⚠️ 注意区分 static vs instance — `m.invoke(null, args)` vs `m.invoke(instance, args)`

### 测试覆盖清单

| 类别 | 阿里云 | 腾讯云 |
|---|---|---|
| 编码 | `percentEncode`: null/" "/`*`/`~`/中文/`=`/`&` | — |
| 签名 | `hmacSha1`: 确定性、不同输入不同输出 | `hmac256`: 确定性、不同输入不同输出 |
| 参数构建 | `buildQueryString`: 字母序、编码 | JSON body 构建 |
| 公共参数 | `buildCommonParams`: 必要 key、`Signature` 不在首签参数中 | 必要 header、timestamp 格式 |
| 响应解析 | `createRecordJson`: 字段映射 | `Response.Error` vs `Response.SendStatusSet` |
| 集成 | 固定 nonce+timestamp → 签名确定性 | 固定 timestamp → 签名确定性 |
| 错误检测 | 假 AK → `json.has("Code")` 为 true | 假 AK → `resp.optJSONObject("Error")` 非空 |

---

## 迁移检查清单

1. [ ] 移除 SDK `<dependency>`，确认无残留传递依赖（`mvn dependency:tree | grep`）
2. [ ] 实现 `buildCommonParams()` + 签名方法 + `doSignedPost()`
3. [ ] API 错误检测：阿里云检查 `json.has("Code")`，腾讯云检查 `resp.optJSONObject("Error")`
4. [ ] `init()`/`getInstance()` 无 SDK 异常声明（若之前有 `throws Exception` 可移除）
5. [ ] 外部接口不变（`ISms`、`IDomain`、`SmsBase` 等）
6. [ ] 写反射测试覆盖签名/编码/参数构建/错误检测
7. [ ] `mvn compile && mvn test -Dtest="<TestClass>"` 通过

### 本项目实际清理结果

| 模块 | 已移除依赖 | 清理的传递依赖数 |
|---|---|---|
| 阿里云短信 | `dysmsapi20170525:4.5.1` | 6 (`tea-openapi`, `tea-xml`, `dom4j`, `json-path`, `jacoco agent`, `accessors-smart/asm`) |
| 阿里云 DNS | `alidns20150109:4.4.1` | 6 (`darabonba-*-util`×5, `bcprov-jdk15on`) |
| 腾讯云短信 | `tencentcloud-sdk-java-sms:3.1.893` | 4 (`tencentcloud-sdk-java-common`, `okhttp`, `okio`, `commons-logging`, `ini4j`) |

**合计移除 3 个直接依赖 + 16 个传递依赖。** 项目 `pom.xml` 中**零云 SDK 依赖**。
