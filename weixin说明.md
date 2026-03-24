# weixin 说明

## 1. 模块定位

`weixin` 模块用于封装微信公众号、小程序、企业微信（公司号）以及相关绑定、消息处理、二维码、支付证书等能力。

它不是一个独立的微信 SDK，而是建立在：

- `com.gdxsoft.weixin.*`
- 本地表配置 `BAS_WX_CFG`
- 商户表 `SUP_MAIN`

之上的业务封装层。

## 2. 模块分层

### 基础配置层

- `WeiXinBase`

### 公众号 / 小程序 / 企业微信主入口

- `WeiXin`
- `QyWeiXin`
- `WeiXinLocal`

### 绑定与业务辅助

- `WeiXinBindAdm`
- `WeiXinBindCustomer`
- `WeiXinPostMsgHandle`
- `WeiXinMediaUtils`
- `WeiXinRedPackage`
- `WxMiniQrCode`

## 3. 关键类说明

### 3.1 `WeiXinBase`

这是所有微信相关能力的基础类。

它负责：

- 管理数据库连接名 `ConnStr`
- 管理数据库前缀 `DbPrefix`
- 根据 `sup_unid + wx_cfg_no` 初始化微信配置
- 从 `BAS_WX_CFG` + `SUP_MAIN` 中读取：
  - `WX_APP_ID`
  - `WX_APP_SECRET`
  - `WX_APP_TOKEN`
  - `WX_PAY_ID`
  - `WX_PAY_KEY`
  - `WX_CFG_TYPE`
  - `WX_CFG_NAME`

它本质上是“从商户配置表中解析微信能力配置”的入口。

### 3.2 `WeiXin`

这是公众号 / 小程序能力的主类，也是 `weixin` 模块中最大的核心类。

从代码结构可看出，它负责：

- 缓存微信配置实例
- 按 `sup_id`、`sup_unid`、`appId`、`wx_cfg_no` 获取配置
- 根据配置初始化：
  - `Config`
  - `Html`
  - `QyConfig`
  - `QyHtml`
- 初始化微信支付证书
- 处理用户认证、ticket、jsapi 签名等能力

一个重要分支是：

- 如果配置是企业微信 / 公司号，则走 `QyConfig`
- 否则走公众号的 `Config` / `Html`

也就是说，`WeiXin` 实际兼容了多种微信类型，只是不同配置走不同底层对象。

### 3.3 `QyWeiXin`

这是企业微信（公司号）同步能力的业务类。

主要负责：

- 初始化企业微信配置
- 同步部门
- 同步员工
- 把企业微信部门 / 员工映射关系记录到本地表 `ADM_USER_DEFAULT`

典型能力包括：

- `departmentsList()`
- `departmentsAdd()`
- `departmentsUpdate()`
- `departmentsDelete()`
- `staffsList()`
- `staffsAdd()`
- `staffsUpdate()`
- `staffsDelete()`

它说明这个模块不仅做微信接口调用，还做“企业微信组织架构与本地组织架构同步”。

### 3.4 `WeiXinLocal`

用于处理微信服务器推送消息的本地入口。

核心职责：

- 处理微信服务器 GET 验证请求
- 处理 POST 消息推送
- 解析 XML 消息
- 记录收到的消息到 `WX_MSG_RECV`
- 调用 `WeiXinPostMsgHandle` 继续业务处理

典型入口：

- `handleWxMessage(HttpServletRequest request)`

因此它更像“公众号服务器回调入口”。

### 3.5 `WeiXinBindAdm`

用于微信与 `ADM_USER` 的绑定或登录。

特点：

- 绑定关系依赖验证记录机制
- 支持微信侧回调后验证数据合法性
- 绑定 / 登录类型通过参数区分

适用于后台用户、ERP 用户等管理员类型绑定微信。

### 3.6 `WeiXinBindCustomer`

用于微信与客户 / 用户相关的绑定认证。

从代码可见，它支持：

- 根据姓名 + 身份证 / 电话 / 护照等信息做认证
- 尝试触发绑定红包
- 把认证中间结果存到 session

这说明它更偏面向业务客户场景，而不是管理端。

### 3.7 `WxMiniQrCode`

用于微信小程序码处理。

支持：

- 替换二维码中心 logo
- 叠加文字
- 输出新的二维码图片

适合需要对小程序码做定制包装的场景。

## 4. 配置来源

`weixin` 模块最关键的配置来源是：

- `BAS_WX_CFG`

结合：

- `SUP_MAIN`

共同决定当前商户的微信配置。

关键字段包括：

- `WX_CFG_NO`
- `WX_CFG_TYPE`
- `WX_APP_ID`
- `WX_APP_SECRET`
- `WX_APP_TOKEN`
- `WX_AGENT_ID`
- `WX_PAY_ID`
- `WX_PAY_KEY`
- `WX_PAY_P12`

也就是说，微信能力是**按商户配置分隔**的。

## 5. 工作方式

## 5.1 配置缓存

`WeiXin` 和 `WeiXinLocal` 都会缓存实例，避免频繁重复初始化微信配置。

这对：

- access token 获取
- ticket 获取
- 配置重复加载

都有性能意义。

需要特别注意的是，`WeiXin` 的主缓存键实际上偏向使用 `wx_cfg_no`。这意味着在使用上默认假设 `wx_cfg_no` 全局唯一；如果部署场景里不同商户可能复用同一个编号，就需要格外小心缓存命中问题。

## 5.2 消息处理链

微信公众号消息回调的典型流程是：

1. `WeiXinLocal.handleWxMessage(request)`
2. GET 时做签名验证
3. POST 时解析微信 XML 消息
4. 写入 `WX_MSG_RECV`
5. 调用 `WeiXinPostMsgHandle`

所以消息并不是收到后直接在控制器里处理，而是先记录，再进入业务处理类。

## 5.3 企业微信同步链

企业微信组织同步的流程通常是：

1. `QyWeiXin.init(rv)`
2. 从 `BAS_WX_CFG` 读取公司号配置
3. 调用企业微信接口获取 / 创建 / 更新部门和员工
4. 把同步映射关系写入 `ADM_USER_DEFAULT`

因此本地与企业微信之间存在一层“同步映射表”。

## 5.4 支付与证书

`WeiXin` 初始化时会尝试读取 `WX_PAY_P12` 证书数据。

如果成功初始化 SSL 上下文，则说明后续可用于微信支付相关能力。

## 6. 典型依赖表

从代码可见，常用表包括：

- `BAS_WX_CFG`
- `SUP_MAIN`
- `ADM_USER_DEFAULT`
- `ADM_DEPT`
- `ADM_USER`
- `V_ADM_USER_POST_DEPT`
- `WX_MSG_RECV`
- 若干红包、回复规则、用户绑定相关表

这说明 `weixin` 模块不是纯接口封装，而是深度绑定本地业务表。

## 7. 使用与维护时要注意

1. 先确认 `WX_CFG_TYPE`，再判断应该走公众号还是企业微信逻辑。
2. `WeiXinBase` 的 `ConnStr` / `DbPrefix` 是全局静态配置，初始化顺序要稳定。
3. 微信消息入口 `WeiXinLocal` 会把收到的 XML 入库，排查问题时要先看库表记录。
4. 企业微信同步会使用 `ADM_USER_DEFAULT` 保存映射关系，清理或重建同步前要考虑本地缓存关系。
5. 绑定类（`WeiXinBindAdm` / `WeiXinBindCustomer`）依赖业务验证参数和 session，不是简单接口调用。
6. 与微信身份绑定相关的 `AUTH_WEIXIN_ID` 在代码里有明显的大小写敏感假设，相关表字段、排序规则和 SQL 处理不要轻易改成大小写不敏感模式。

## 8. 总结

可以把 `weixin` 模块理解为：

**基于商户配置表驱动的微信业务能力层**

其中：

- `WeiXinBase` 管配置
- `WeiXin` 管公众号 / 小程序 / 部分公司号能力
- `QyWeiXin` 管企业微信组织同步
- `WeiXinLocal` 管微信服务器回调
- 绑定、红包、小程序码等类承载具体业务扩展

如果后续扩展微信能力，优先复用现有配置体系和 `BAS_WX_CFG` 的商户隔离模式，而不要直接在业务代码里硬编码 appid / secret。
