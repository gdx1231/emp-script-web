# app 说明

## 1. 模块定位

`app` 模块用于为移动端、微信内嵌页、小程序和原生 App 容器提供运行环境判断、启动参数解析和菜单配置辅助。

它不是一个完整 App 后端，而是一个：

- 运行环境识别层
- 页面初始化辅助层
- 小程序场景参数处理层

## 2. 关键类

### 2.1 `App`

这是 `app` 模块的基础入口类。

主要职责：

- 根据 `User-Agent` 判断当前环境
- 输出前端可用的环境 JS / CSS 标记
- 处理引荐人参数
- 获取代理、用户等基础数据
- 判断 App 启动语言

从代码看，它重点识别这些环境：

- 微信内
- 原生 App 内
- 小程序内
- iPhone
- Android
- iPad

### 2.2 `AppMini`

这是面向小程序场景参数的辅助类。

主要职责：

- 解析 `INIT_CFG`
- 提取 `scene`
- 根据场景模式生成 Web 参数
- 检查用户等级
- 检查代理信息
- 根据 `open_id + wx_cfg_no` 反查是否已有本地用户

它说明这套代码里“小程序启动参数”是一个明确的业务入口。

### 2.3 `App2CfgBase`

这是 App 页面配置与菜单输出的基础类。

主要职责：

- 检查语言
- 检查是否原生 App
- 检查是否登录
- 查询 `_EWA_APP_CFG2` 配置
- 生成首页 4 格菜单
- 生成 footer 菜单

也就是说，它负责把数据库配置转成 App 端可直接渲染的菜单 HTML / JS。

### 2.4 `IApp2Cfg`

这是配置输出接口。

约定了：

- 获取 cfg SQL
- 获取首页菜单
- 获取 footer
- 检查语言 / 登录 / App 环境

因此如果后续有不同 App 配置实现，应继续按这个接口扩展。

## 3. 工作方式

## 3.1 环境识别

`App` 构造时会读取 `SYS_USER_AGENT`，并判断：

- `micromessenger` -> 微信
- `/native` -> 原生 App
- `miniprogram` -> 小程序
- `iphone` / `ipad` / `android` -> 设备类型

然后可输出：

- `createAppEnvJs()`
- `createAppEnvCss()`

把这些判断结果直接注入前端页面。

## 3.2 语言判断

`App.appStartLang(...)` 会综合这些来源决定语言：

1. 请求参数 `ewa_lang`
2. cookie `APP_LANG`
3. session 里的 `SYS_EWA_LANG`
4. 浏览器 `accept-language`

这说明 App 启动语言不是单一参数控制，而是有一套优先级。

## 3.3 小程序场景参数

`AppMini.initScenes()` 会解析：

- `INIT_CFG`

其中最重要的是：

- `scene`

并把第一段作为：

- `sceneMethod`

后续不同场景逻辑再根据这个 `sceneMethod` 分发。

## 3.4 小程序用户映射

`AppMini.checkExistsUserByOpenId()` 会根据：

- `open_id`
- `wx_cfg_no`

到 `WxUser` / `WebUser` 里查已有映射，成功后会生成一个 AES 编码值返回给前端。

这说明 `app` 模块与微信用户绑定链路是直接打通的。

## 3.5 菜单配置输出

`App2CfgBase` 从数据库表：

- `_EWA_APP_CFG2`
- `_EWA_APP_CFG2_GRP_subs`

查询配置，然后生成：

- 初始化配置 JS
- 首页菜单 HTML
- footer 菜单 HTML

所以 App 菜单不是硬编码在前端，而是数据库驱动。

## 4. 与其它模块的关系

- 与 `dao` 配合：大量直接使用 `WebUserDao`、`SupMainDao`、`SysDefaultDao`、`WxUserDao`
- 与 `weixin` 配合：小程序 / openid 场景直接依赖微信用户数据
- 与 `user` 配合：用户身份和等级数据会进入小程序场景处理
- 与 EWA / EMP 配合：菜单和页面配置明显是围绕 EMP 运行时组织的

## 5. 使用与维护时要注意

1. `App` 的环境识别高度依赖 `User-Agent`，改原生容器标识时要同步修改 `App.NATIVE_TAG` 约定。
2. `AppMini` 的入口参数 `INIT_CFG` 是核心协议字段，前后端都要保持一致。
3. `App2CfgBase.checkLogined()` 是直接查 `adm_user`，说明这里的“已登录”更偏后台 / 商户上下文，不一定等于普通用户登录。
4. App 菜单来自数据库配置表，排查菜单缺失时应优先查配置表而不是 Java 代码。
5. 小程序场景里存在用户映射、代理信息、引荐人等业务参数，不能只把它理解成纯前端壳层。

## 6. 总结

可以把 `app` 模块理解为：

**移动端 / 微信 / 小程序的运行环境与页面初始化辅助层**

其中：

- `App` 管环境识别与基础上下文
- `AppMini` 管小程序场景参数
- `App2CfgBase` / `IApp2Cfg` 管菜单和配置输出

如果后续继续整理 App 相关能力，建议沿着“环境识别 -> 启动参数 -> 配置菜单 -> 用户映射”这条线理解，而不要把它误看成单一页面工具类。
