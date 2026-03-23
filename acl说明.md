# acl 说明

## 1. 模块定位

`acl` 模块用于定义本仓库里页面或功能的登录态判断规则，并给未登录请求提供统一跳转入口。

它不是细粒度 RBAC 权限系统，而更偏向：

- 登录态判定
- 入口访问控制
- 登录跳转 URL 生成
- 管理员 / 商户 / 用户三类上下文区分

## 2. 关键类

### 2.1 `AclBase`

这是所有 ACL 实现类的基础类。

最重要的约定是：

`AclBase.getString(...)` 只信任 `RequestValue` 中两类来源的值：

- `PageValueTag.COOKIE_ENCYRPT`
- `PageValueTag.SESSION`

如果某个值只是普通请求参数，即使名字叫 `G_ADM_ID`、`G_USR_ID`，这里也不会认。

这也是整个 ACL 体系最关键的安全边界。

### 2.2 `Login`

`Login` 是 ACL 相关的公共工具类。

主要职责：

- 判断用户 / 商户 / 管理员是否已登录
- 读取 `G_USR_ID`、`G_SUP_ID`、`G_ADM_ID`
- 生成登录页跳转地址
- 清理 session 与 cookie
- 生成管理员自动登录 token

它既服务 ACL，也服务登录相关业务模块。

### 2.3 `AdminImpl`

后台管理员 ACL。

判定条件：

- `G_ADM_ID`
- `G_SUP_ID`

都必须有效。

未登录时跳转到：

- `/login`

### 2.4 `BackAdminImpl`

后台管理入口 ACL。

未登录时跳转到：

- `/back_admin/login`

它和 `AdminImpl` 的差别主要在登录入口 URL。

### 2.5 `BusinessImpl`

商户业务模块 ACL。

未登录时跳转到：

- `/business/login.jsp`

它还兼容了某些 `websocket` 场景下 `RequestValue.getRequest()` 为空的情况。

### 2.6 `CustomerImpl`

用户侧 ACL。

判定核心：

- `G_USR_ID`

未登录时跳转到：

- `/customer/login-or-register`

## 3. 登录态判定方式

从 `Login` 与 `AclBase` 的组合可以看出，仓库里主要有三类登录态：

### 用户登录态

- `G_USR_ID`

### 商户登录态

- `G_SUP_ID`

### 管理员登录态

- `G_ADM_ID`

并且登录态值必须来自：

- session
- 加密 cookie

而不是普通请求参数。

## 4. 典型调用链

### 4.1 页面 ACL

页面请求
-> 某个 `IAcl` 实现类
-> 调 `canRun()`
-> 失败则 `setGoToUrl(...)`
-> 页面跳转到登录入口

### 4.2 登录判断

`Login.isUserLogined(rv)`
-> `getLoginedUserId(rv)`
-> `getLoginedParameter(rv, "G_USR_ID")`

### 4.3 管理员自动登录

`Login.createAdminLoginToken(...)`
-> AES 加密管理员信息
-> `createAdminLoginValidByToken(...)`
-> 创建 `WEB_USER_FPWD` 验证记录

这说明管理员自动登录与用户自动登录一样，也复用了统一验证记录机制。

## 5. 登录跳转机制

`Login.gotoLogin(...)` 会：

1. 带上语言参数 `EWA_LANG`
2. 读取当前请求路径与查询串
3. 组装 `ref`
4. 把当前页面地址编码后拼到登录地址

因此登录完成后，调用方通常可以回跳到原始访问页面。

## 6. 与其它模块的关系

- 与 `user` 模块配合：`user` 负责登录过程，`acl` 负责识别登录结果
- 与 EWA / EMP 配合：很多页面的访问控制通过 ACL + EWA 页面运行时一起生效
- 与 cookie / session 配合：真正可信的登录态来源就是这两者

## 7. 使用与维护时要注意

1. 不要用普通请求参数伪造 `G_USR_ID`、`G_ADM_ID`、`G_SUP_ID`，`AclBase` 不会认。
2. 新的 ACL 实现如果需要登录判断，优先继承 `AclBase`，保持同样的安全边界。
3. `Login` 里的登录跳转会拼 `ref` 参数，改登录页时要确保回跳逻辑仍然可用。
4. `BusinessImpl` 里对 `websocket` 场景做了保护，说明某些 ACL 并不只跑在普通页面请求里。
5. `setItemName(...)` 当前实现里实际写到了 `_XmlName`，维护时如果碰到 ACL 元数据异常，要先注意这里的历史实现细节。

## 8. 总结

可以把 `acl` 模块理解为：

**基于 session / 加密 cookie 的轻量登录态访问控制层**

其中：

- `AclBase` 定义可信登录态来源
- `Login` 提供统一登录态判断与跳转工具
- `AdminImpl` / `BackAdminImpl` / `BusinessImpl` / `CustomerImpl` 分别服务不同访问入口

如果后续新增权限入口，建议继续沿用现有模式：先确认登录态可信来源，再做最小化的入口访问控制。
