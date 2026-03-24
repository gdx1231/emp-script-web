# message 说明（sms 与 sendmail）

## 1. 模块定位

`message` 相关代码主要分为两类能力：

- **短信发送（sms）**
- **邮件发送（sendmail / email）**

这两部分都不是单纯的“立即调用第三方接口”，而是同时支持：

- 直接发送
- 入库排队
- 去重控制
- 任务调度发送
- 与 EMP / EasyWeb 的 `HtmlControl`、`RequestValue`、数据库表结构联动

从仓库结构看：

- 短信底层实现位于 `com.gdxsoft.message.sms`
- 短信业务封装位于 `com.gdxsoft.web.message.sms`
- 邮件能力位于 `com.gdxsoft.web.message.email`
- 定时任务发送位于 `com.gdxsoft.web.job`

## 2. 短信模块（sms）

## 2.1 相关类

### 底层接口与实现

- `com.gdxsoft.message.sms.ISms`
- `com.gdxsoft.message.sms.SmsBase`
- `com.gdxsoft.message.sms.SmsAliImpl`
- `com.gdxsoft.message.sms.SmsTencentImpl`
- `com.gdxsoft.message.sms.SmsMyMobKit`

### 业务封装

- `com.gdxsoft.web.message.sms.SendSms`
- `com.gdxsoft.web.user.SmsValid`
- `com.gdxsoft.web.job.JobSms`

## 2.2 `ISms` 接口

`ISms` 是短信发送的统一抽象，定义了：

- 服务商标识 `getProvider()`
- 基础配置：
  - `accessKeyId`
  - `accessKeySecret`
  - `smsTemplateCode`
  - `smsSignName`
  - `regionId`
  - `smsAppId`
- 发送方法：
  - 单号码发送
  - 多号码发送
  - 列表发送
  - 发送并查询结果

也就是说，业务层基本只依赖 `ISms`，而不依赖具体供应商。

## 2.3 `SmsBase`

`SmsBase` 是短信实现的公共父类，主要提供：

- 公共配置属性
- 手机号标准化工具：
  - `replacePhoneHz(...)`：替换全角数字
  - `chinesePhoneAddPlus86(...)`：为腾讯云格式补 `+86`
  - `chinesePhoneRemovePlus86(...)`：为阿里云格式去掉 `+86`

这说明仓库内部已经考虑了不同短信供应商对手机号格式的要求差异。

## 2.4 阿里云短信：`SmsAliImpl`

`SmsAliImpl` 实现了阿里云短信发送。

特点：

- 默认区域：`cn-hangzhou`
- 手机号会去掉 `+86`
- 支持单发、多发
- `sendSmsAndGetResponse(...)` 会在发送后进一步查询回执明细
- 返回值是 `JSONObject`，包含：
  - 发送结果码
  - 请求 ID
  - 业务流水号
  - 签名
  - 模板号
  - 模板参数
  - 查询回执结果

适合需要查看阿里云明细返回的场景。

## 2.5 腾讯云短信：`SmsTencentImpl`

`SmsTencentImpl` 实现了腾讯云短信发送。

特点：

- 默认区域：`ap-beijing`
- 手机号会补成 `+86xxxxxxxxxxx`
- 模板参数采用腾讯云的序号式参数格式：
  - `1`
  - `2`
  - `3`
- 会自动把 `JSONObject` 转成腾讯云 `TemplateParamSet`
- 结果返回 `JSONObject`

这里有一个很重要的差异：

- 阿里云通常按键值模板参数传 JSON
- 腾讯云实现里按 `"1"`, `"2"` 这种顺序参数取值

因此切换供应商时，模板参数结构需要一起考虑。

## 2.6 `SendSms`

`SendSms` 是短信业务层的核心封装类。

它不是底层短信 SDK 包装，而是把：

- `HtmlCreator`
- `RequestValue`
- 短信模板参数
- 手机号列表
- 去重逻辑
- 入库逻辑
- 直接发送逻辑

统一封装起来。

### 主要静态方法

- `smsSend(ISms sms, HtmlCreator hc, String templateCode)`
  - 基于当前 `HtmlCreator` 立即发短信
- `smsQueue(ISms sms, HtmlCreator hc, String templateCode)`
  - 基于当前 `HtmlCreator` 仅写入短信队列
- `checkExists(...)`
  - 检查是否存在重复短信

### 初始化逻辑

`init(HtmlCreator hc)` 会做这些事：

1. 克隆当前请求上下文 `RequestValue`
2. 从 `HtmlCreator` 的结果表中提取接收手机号
3. 从结果表中提取短信模板参数
4. 自动生成引用信息 `MSG_REF_TABLE`
5. 做重复发送检查

### 短信接收人与模板参数

它约定数据表里使用这些字段：

- `PHONE`
- `SMS_DATA`
- `MSG_REF_TABLE`
- `MSG_REF_ID`

其中：

- `PHONE` 用于提取手机号
- `SMS_DATA` 所在行会被转成模板参数 JSON

### 入库表

`SendSms.saveToQueue()` 会写入：

- `sms_job`
- `sms_job_lst`

其中：

- `sms_job`：短信主记录
- `sms_job_lst`：每个手机号一条子记录

保存内容包括：

- 模板号
- 签名
- 供应商
- 模板 JSON
- 关联引用表 / 引用记录
- 发送状态
- 发送日志

### 去重逻辑

`SendSms` 的去重不是只按手机号，而是同时考虑：

- 模板编号
- 模板参数内容
- 手机号集合
- 一定时间窗口内是否已发送

去重核心依赖：

- `Utils.md5(templateCode + "GDX" + template)`
- 表：`sms_job` / `sms_job_lst`

这意味着**相同模板 + 相同内容**在短时间内不会被重复发送。

## 2.7 `SmsValid`

`SmsValid` 属于用户登录/验证码链路中的短信封装。

它和 `SendSms` 的区别是：

- `SendSms` 更偏业务通知短信
- `SmsValid` 更偏验证码 / 登录验证短信

### 主要职责

- 手机号合法性检查
- 发送频次检查
- 生成验证码
- 创建验证记录
- 调用短信接口发送验证码
- 在发送失败时删除已生成的验证码记录

### 相关验证记录

`SmsValid` 依赖 `ValidBase`，把验证码信息写入验证记录表（如 `WEB_USER_FPWD` 对应机制）。

典型流程是：

1. 校验手机号是否存在
2. 生成 6 位验证码
3. 创建验证记录
4. 调用 `ISms.sendSms(...)`
5. 发送成功则保留记录
6. 发送失败则删除记录

因此它不是“先发短信后记录”，而是“先建验证记录，再发短信，失败则回滚验证记录”。

## 2.8 `JobSms`

`JobSms` 是短信队列的后台发送任务。

它会从：

- `sms_job`
- `sms_job_lst`

取出状态为：

- `SMS_JOB_NEW`

的短信记录进行发送。

### 主要逻辑

1. 读取待发短信
2. 检查手机号是否为空
3. 再做一次重复检查
4. 根据供应商调用发送接口
5. 更新 `sms_job` 状态
6. 把发送结果写回 `sms_job_lst.SMS_RESULT`

### 状态示例

从代码可见的状态包括：

- `SMS_JOB_NEW`
- `SMS_JOB_SEND`
- `SMS_JOB_FAIL`
- `SMS_JOB_REPEAT`
- `SMS_JOB_NO_PHONE`

也就是说，短信体系是**支持异步队列发送与结果跟踪**的，不只是实时调用第三方接口。

## 3. 邮件模块（sendmail）

## 3.1 相关类

- `com.gdxsoft.web.message.email.SendMessage`
- `com.gdxsoft.web.message.email.MsgUtils`
- `com.gdxsoft.web.message.email.SendSysMessageInfoMail`
- `com.gdxsoft.web.job.JobMailSender`
- `com.gdxsoft.web.job.JobMailScan`
- `com.gdxsoft.web.job.JobMailBounce`

其中当前最关键的是：

- `SendMessage`
- `MsgUtils`
- `JobMailSender`

## 3.2 `SendMessage`

`SendMessage` 是邮件发送的核心业务封装。

它的能力包括：

- 从 EMP XML + `HtmlControl` 生成邮件正文
- 从查询结果中提取收件人、抄送、密送、发件人、回复人
- 构造 `SendMail`
- 直接发送
- 写入发送队列
- 对重复邮件做 MD5 去重

### 两个入口方法

- `mailQueue(xmlName, itemName, rv)`
  - 仅写入邮件队列
- `mailSend(xmlName, itemName, rv)`
  - 立即发送，并写入队列表

### 初始化方式

`init(xmlName, itemName, RequestValue rv)` 的核心流程是：

1. 克隆请求参数
2. 清理一些当前页面动作参数
3. 通过 `HtmlControl.init(...)` 执行指定 XML / item
4. 用 `ht.getHtml()` 作为邮件正文
5. 用 `ht.getTitle()` 作为邮件标题
6. 从 `HtmlControl` 结果表中提取 To / Cc / Bcc / From / Sender / Reply-To

这说明邮件内容不是手工字符串拼接，而是明显依赖 EMP 页面 / XML 输出能力。

### 收件人字段约定

`SendMessage` 在结果表中识别这些字段：

- `TO_EMAIL` / `TO_NAME`
- `CC_EMAIL` / `CC_NAME`
- `BCC_EMAIL` / `BCC_NAME`
- `FROM_EMAIL` / `FROM_NAME`
- `SENDER_EMAIL` / `SENDER_NAME`
- `REPLAY_TO_EMAIL` / `REPLAY_TO_NAME`

如果结果中没有发件人：

- 会使用默认值：
  - `undefined@undefined.gdx`
  - `undefined`

### 队列表

邮件队列主要写入：

- `sys_message_info`

入库内容包括：

- 发件人信息
- 收件人信息
- 邮件标题
- 邮件正文
- 附件 JSON
- 回复地址
- SMTP 配置
- MQ 消息 ID / MQ 消息
- 关联业务表 / 关联记录
- 邮件 MD5
- 邮件 Message-ID

### 去重逻辑

邮件去重通过 `MESSAGE_MD5` 完成。

计算内容包括：

- 引用表 / 引用 ID
- 标题
- 正文
- 发件人
- 收件人
- 抄送
- 密送
- 回复人
- 附件

如果 `sys_message_info` 中已存在同样的 `MESSAGE_MD5`，则视为重复邮件，不再重复入队或发送。

## 3.3 `MsgUtils`

`MsgUtils` 是邮件相关的辅助工具类。

主要提供：

- 根据 `adm_user` 获取管理员邮箱地址
- 根据 `web_user` 获取用户邮箱地址
- 标准化多邮箱字符串分隔符
- 把附件 JSON 解析为物理文件列表

其中附件解析支持多种 JSON 格式字段：

- `UP_URL`
- `URL`
- `path`

并会自动把上传 URL 映射到真实物理路径。

这说明邮件附件默认是与上传目录体系联动的。

## 3.4 `JobMailSender`

`JobMailSender` 是邮件发送的后台任务。

它会从 `sys_message_info` 中取出：

- `message_status = 'NO'`
- 或 `message_status is null`

且同时满足：

- `from_email != ''`
- `target_email != ''`

的记录进行发送。

如果 `MAIL_TYPE = CALENDER`，则按日历邮件流程发送；否则按普通邮件流程发送。

因此邮件体系同样支持：

- 先入队
- 后台统一发送

的工作模式。

## 4. 两套消息体系的共同特点

短信与邮件虽然走不同表、不同实现，但有几个共同点：

### 4.1 都支持“立即发送”和“排队发送”

- 短信：`sendNow()` / `saveToQueue()`
- 邮件：`mailSend()` / `mailQueue()`

### 4.2 都做重复控制

- 短信：基于模板和内容做 MD5 / 时间窗口检查
- 邮件：基于标题、正文、收件人、附件等做 `MESSAGE_MD5`

### 4.3 都依赖 `RequestValue`

发送链路里的：

- 引用关系
- 发送人
- 当前商户
- 当前管理员
- 系统时间

都通过 `RequestValue` 传递和落库。

### 4.4 都能和 EMP XML / `HtmlControl` 联动

- 邮件正文和收件人可由 `HtmlControl` 输出结果决定
- 短信接收人与模板参数也可以从 `HtmlCreator` 的结果表中提取

这体现了这个仓库一贯的设计风格：**配置驱动 + 数据驱动 + 队列化**。

## 5. 如何选择使用方式

### 5.1 适合用 `SendSms`

如果你需要的是：

- 业务通知短信
- 批量手机号发送
- 支持去重
- 支持入队
- 支持后台任务统一发送

优先使用：

- `com.gdxsoft.web.message.sms.SendSms`

### 5.2 适合用 `SmsValid`

如果你需要的是：

- 登录验证码
- 身份验证短信
- 与用户验证记录联动

优先使用：

- `com.gdxsoft.web.user.SmsValid`

### 5.3 适合用 `SendMessage`

如果你需要的是：

- 邮件正文来自 XML 页面 / 模板
- 收件人来自 EMP 查询结果
- 需要写入 `sys_message_info`
- 需要防重复
- 需要后台任务发送

优先使用：

- `com.gdxsoft.web.message.email.SendMessage`

## 6. 使用时需要特别注意

1. 腾讯云短信模板参数采用序号键，不是任意命名键。
2. 阿里云和腾讯云对手机号格式要求不同，仓库已经分别做了处理，不要绕过 `SmsBase` 工具方法。
3. 短信和邮件都默认有去重逻辑，若出现“未发送”现象，要先检查是否被判定为重复。
4. `SendMessage` 依赖 `HtmlControl` 生成正文和收件人，修改邮件内容时通常不只改 Java，还要看 XML / item 输出。
5. `SmsValid` 在发送失败时会删除验证码记录，验证码类逻辑不要只盯短信结果，还要注意验证表状态。

## 7. 总结

可以把这一块理解为：

**一个面向企业业务通知的消息发送框架**

其中：

- `ISms` + `SmsAliImpl` / `SmsTencentImpl` 负责短信供应商适配
- `SendSms` 负责业务短信队列与去重
- `SmsValid` 负责验证码短信
- `SendMessage` 负责邮件内容生成、入队和发送
- `JobSms` / `JobMailSender` 负责后台发送任务

如果后续要扩展新的消息能力，优先保持现有模式：

- 供应商适配层
- 业务封装层
- 队列表
- 后台任务发送

而不要直接在业务代码里散落第三方 SDK 调用。
