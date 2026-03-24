# user 说明

## 1. 模块定位

`user` 模块负责围绕 `WEB_USER` 的登录、注册、短信验证码验证和自动登录能力。

它不是完整的会员中心，而是把：

- 页面登录
- 短信登录
- 自动登录
- 验证码记录

封装成一组可复用的服务类。

## 2. 关键类

### 2.1 `RegisterOrLogin`

这是 `user` 模块的主要业务入口。

它负责：

- 根据用户名 / 手机号 + 密码登录
- 登录或注册页输出
- 短信登录发送验证码
- 短信验证码校验
- 自动登录判断
- 可选短信自动注册

它内部用 `method` 分发不同动作，例如：

- `login`
- `loginOrRegister`
- `register`
- `auto_login`
- `smslogin_send`
- `smslogin_valid`
- `loginByPwd`

说明这个类本质上是一个“用户登录动作分发器”。

### 2.2 `DoLogin`

这是登录态写入与自动登录的核心类。

主要职责：

- 执行自动登录
- 生成自动登录凭证
- 校验自动登录凭证
- 写入浏览器 cookie
- 调用 EWA 登录页面完成最终登录

它的关键设计是：

- 自动登录不是直接凭用户 ID 登录
- 而是先创建一条 `WEB_USER_FPWD` 验证记录
- 再把 `FP_UNID + FP_VALIDCODE + 时间戳` 封装成 AES token

### 2.3 `ValidBase`

这是所有验证码 / 验证记录逻辑的基础类。

主要职责：

- 生成随机数字或字母验证码
- 写入验证记录
- 校验验证码
- 记录验证次数与验证结果
- 删除或变更验证记录类型

核心表是：

- `WEB_USER_FPWD`

它不只服务用户短信登录，也服务管理员自动登录、微信登录等其它验证场景。

### 2.4 `SmsValid`

这是短信验证码的业务封装类。

主要职责：

- 校验手机号合法性
- 校验短信发送频率
- 创建验证码记录
- 调用短信供应商发送验证码
- 成功后记录日志，失败则删除验证记录

它说明短信验证码不是简单“发完就算”，而是和验证记录表、发送频次限制一起工作。

## 3. 工作方式

## 3.1 密码登录

`RegisterOrLogin.loginByPwd(...)` 的流程大致是：

1. 用 `HtmlControl` 跑登录页配置，先过图形验证码等前置校验
2. 从 `WEB_USER` 查用户
3. 使用 `UArgon2.verifyPwd(...)` 校验密码
4. 登录成功后写入用户登录态

这说明密码校验不是明文比对，而是 Argon2 哈希校验。

## 3.2 短信登录

短信登录的典型链路是：

1. `SmsValid` 生成 6 位验证码
2. 在 `WEB_USER_FPWD` 中创建记录
3. 调用短信接口发送
4. 用户提交验证码后走 `checkValidCode(...)`
5. 验证成功后把记录类型切换为可登录类型

这里有一个重要约定：

- 短信校验成功后，记录类型会从 `SMS_WEB_USER_LOGIN` 切到 `WEIXIN_LOGIN` 对应的登录类型使用链路

虽然命名带有 `WEIXIN_LOGIN`，但在这里实际也被拿来承载自动登录记录。

## 3.3 自动登录

`DoLogin` 的自动登录链路是：

1. 浏览器读取 `AUTO_LOGIN_TOKEN`
2. 用 AES 解密得到：
   - `FP_UNID`
   - `FP_VALIDCODE`
   - 创建时间
3. 校验 token 是否过期
4. 查询 `WEB_USER_FPWD`
5. 成功后重新生成一份新的自动登录 token
6. 覆盖浏览器 cookie

所以自动登录采用的是“短期循环换新”的凭证机制，而不是永久 cookie。

## 4. 核心表与记录类型

### 4.1 用户表

- `WEB_USER`

### 4.2 验证记录表

- `WEB_USER_FPWD`

### 4.3 常见 `FP_TYPE`

`ValidBase` 中可以看到几类关键记录类型：

- `WEIXIN_LOGIN`
- `SMS_WEB_USER_LOGIN`
- `B2B_ADM_LOGIN`
- `WX_ADM_LOGIN`

说明这张表是统一验证记录中心，不只是“找回密码”。

## 5. 频次与安全限制

### 5.1 短信频次限制

`SmsValid.checkSmsFrequency(...)` 的规则包括：

- 两次发送间隔不能小于 120 秒
- 一小时内不超过 5 次

记录来源依赖：

- `SMS_JOB`

并且 `SMS_REF_TABLE='SMS_VALID'`。

### 5.2 验证次数限制

`ValidBase.checkValidCode(...)` 会检查：

- 记录是否存在
- 是否超时
- 是否超过最大尝试次数

然后把：

- 当前尝试次数
- 验证结果

回写到 `WEB_USER_FPWD`。

### 5.3 登录态来源限制

实际登录判断最终依赖 `Login` / `AclBase` 对 `RequestValue` 中登录参数的读取，并且只信任：

- `SESSION`
- `COOKIE_ENCYRPT`

这和仓库整体 ACL 约定是一致的。

## 6. 与其它模块的关系

- 与 `acl` 模块配合：登录后由 `Login` / `AclBase` 识别 `G_USR_ID`、`G_ADM_ID` 等登录态
- 与 `message.sms` 配合：真正的短信发送委托给短信提供商实现
- 与 EWA / EMP 配合：页面登录、验证码校验等前置流程依赖 `HtmlControl`

## 7. 使用与维护时要注意

1. `WEB_USER_FPWD` 是统一验证中心，改字段或清理数据时不要只按“找回密码表”理解。
2. 自动登录 token 依赖 AES 解密和验证记录，不能只删 cookie 不删数据库记录。
3. 短信发送失败时会删除已创建的验证记录，这个行为是有意设计，用于避免“发不出去但验证码仍有效”。
4. 密码登录依赖 Argon2 校验，迁移密码算法时要同时检查 `RegisterOrLogin`。
5. 很多登录前置校验并不在 Java 里完全展开，而是依赖 `HtmlControl.init(xmlname, itemname, ...)` 对应的 EWA 配置。

## 8. 总结

可以把 `user` 模块理解为：

**围绕 `WEB_USER` 的登录与验证服务层**

其中：

- `RegisterOrLogin` 管登录动作分发
- `DoLogin` 管自动登录与登录态写入
- `ValidBase` 管统一验证码记录
- `SmsValid` 管短信验证码发送与校验

如果后续扩展用户登录方式，优先复用现有的 `WEB_USER_FPWD` 验证记录机制，而不要旁路新增一套独立凭证体系。
