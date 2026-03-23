# ws 说明

## 1. 模块定位

这块更准确地说不是一个独立的 `ws` 包，而是仓库里对 `easyweb.websocket` 能力的接入与业务处理实现。

也就是说：

- WebSocket 基础设施主要来自外部库 `com.gdxsoft.easyweb.websocket`
- 当前仓库提供的是业务处理器、调用点和测试用例

## 2. 当前仓库里实际可见的 WebSocket 相关内容

从源码看，最明确的 WebSocket 业务实现是：

- `com.gdxsoft.chatRoom.sdk.ClientChatWsImpl`

另外还能看到：

- `TestWs`：验证消息处理器是否可被加载
- `LogBase`：构造 WebSocket 推送消息结构
- `WeiXinWebSocket`：当前仓库里几乎是空壳占位类

因此这里的主线其实是“聊天室消息处理”。

## 3. 核心类

### 3.1 `ClientChatWsImpl`

这是仓库里最实质的 WebSocket 消息处理类。

它：

- 继承 `Thread`
- 实现 `IHandleMsg`
- 使用 `EwaWebSocketBus`
- 通过 `JSONObject command` 接收客户端命令

主要职责：

- 解析 action
- 校验 `userToken`
- 调用聊天 RESTful 服务
- 把结果回推给当前 WebSocket 客户端
- 必要时广播房间消息

### 3.2 `EwaWebSocketBus` / `IHandleMsg`

这两个不是当前仓库定义的，而是来自：

- `com.gdxsoft.easyweb.websocket`

从使用方式可以看出：

- `EwaWebSocketBus` 代表当前 socket 上下文
- `IHandleMsg` 是具体业务处理器接口

当前仓库主要是按这个接口规范实现自己的业务处理类。

### 3.3 `TestWs`

测试代码里：

- 创建 `EwaWebSocketBus`
- 调用 `LoadHandleMessage.getInstance("chat", ...)`

这说明 WebSocket 处理器加载是通过“方法名 / 处理器标识”完成的，`chat` 就是当前仓库已接入的一个处理方法。

## 4. 聊天消息处理链

从 `ClientChatWsImpl` 可见，典型链路大致是：

1. WebSocket 收到客户端命令
2. `LoadHandleMessage` 根据方法名加载处理器
3. 处理器拿到：
   - `socket`
   - `command`
4. 读取 `action`
5. 校验 `userToken`
6. 从配置中读取 `chat_restful_root`
7. 创建 `ClientSdk`
8. 调用后端聊天 REST 接口
9. 把结果 `sendToClient(...)`

这说明这个 WebSocket 层并不承载全部聊天逻辑，而是：

- WebSocket 负责实时收发
- 真正业务动作大量转给 RESTful 服务

## 5. 支持的动作

从 `ClientChatWsImpl.doAction()` 可见，目前支持的动作包括：

- `updateRoomName`
- `rooms`
- `exitRoom`
- `deleteRoomMembers`
- `addRoomMembers`
- `roomMembers`
- `createRoom`
- `friends`
- `post`
- `topics`
- `uploaded`
- `myinfo`
- `delete`

可以看出它主要面向聊天房间、帖子、成员和系统通知。

## 6. 配置与外部依赖

### 6.1 关键配置

最关键的外部配置是：

- `chat_restful_root`

它来自 `ewa_conf.xml` 的 `initPara`。

如果这个参数缺失，`ClientChatWsImpl` 会直接返回错误。

### 6.2 外部依赖

这一块强依赖：

- `com.gdxsoft.easyweb.websocket`
- 聊天 RESTful 服务
- `ClientSdk`

因此当前仓库里的 WebSocket 代码不能孤立理解成“自给自足的即时通讯模块”。

## 7. 与日志 / 通知的关系

`LogBase` 里有创建 WebSocket 消息 JSON 的方法，说明日志或消息提醒能力也可能通过 WebSocket 向前端广播。

但从当前仓库代码看，这部分更多是消息结构辅助，不是完整推送中心。

## 8. 使用与维护时要注意

1. 当前仓库没有完整 WebSocket 容器实现，核心运行时来自 `easyweb.websocket`。
2. 聊天 WebSocket 处理器只是实时入口，业务大头仍在 RESTful 服务。
3. 部署时必须确认 `chat_restful_root` 已配置，否则聊天处理器无法工作。
4. 如果新增 WebSocket 业务，最好沿用 `IHandleMsg` + `LoadHandleMessage` 的现有模式。
5. `WeiXinWebSocket` 当前不是成熟实现，不要把它当成现成能力入口。

## 9. 总结

可以把这块理解为：

**基于 `easyweb.websocket` 的业务接入层**

其中：

- 外部库负责 WebSocket 基础设施
- `ClientChatWsImpl` 负责聊天消息业务处理
- `TestWs` 验证处理器加载链路

如果后续要继续整理实时通信能力，应该优先把“WebSocket 入口”和“聊天 RESTful 服务”一起看，而不是只盯当前仓库这部分代码。
