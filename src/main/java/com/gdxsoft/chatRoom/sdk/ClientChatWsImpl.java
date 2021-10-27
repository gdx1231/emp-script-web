package com.gdxsoft.chatRoom.sdk;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.chatRoom.dao.ChatUser;
import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.servlets.RestfulResult;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.easyweb.websocket.*;

/**
 * WebScocket 话题消息的处理
 */
public class ClientChatWsImpl extends Thread implements IHandleMsg {
	private static Logger LOGGER = LoggerFactory.getLogger(ClientChatWsImpl.class);

	private static Map<Long, String> USER_MAP = new ConcurrentHashMap<>();

	public final static String METHOD = "chat";

	// 推送帖子列表消息标志
	public final static String CHAT_BROAD_MSG_ID = "chat_broad_msg";
	// 广播删除帖子
	public final static String CHAT_BROAD_DELETE_ID = "chat_broad_delete_it";
	// 广播新房间创建了
	public final static String CHAT_BROAD_ROOM_CREATED = "chat_broad_room_created";
	// 被踢出房间
	public final static String CHAT_BROAD_ROOM_KICKED = "chat_broad_room_kicked";
	// 解散房间
	public final static String CHAT_BROAD_ROOM_DISMISS = "chat_broad_room_dismiss";
	// 解散房间
	public final static String CHAT_BROAD_ROOM_NAME_CHANGED = "chat_broad_room_name_changed";
	private EwaWebSocketBus socket;
	private JSONObject command;
	private String action;

	private long chatRoomId;

	private ClientSdk client;

	/**
	 * 初始化对象
	 * 
	 * @param socket  EwaWebSocket
	 * @param command 调用的命令
	 */
	public ClientChatWsImpl(EwaWebSocketBus socket, JSONObject command) {
		this.socket = socket;
		this.command = command;
		this.action = command.optString("action");
		if (StringUtils.isBlank(this.action)) {
			this.action = command.optString("ACTION");
		}
		if (this.action == null) {
			this.action = "";
		}
	}

	@Override
	public void run() {
		JSONObject result = null;

		String commandId = null;// 客户端提交的ID
		if (this.command != null && this.command.has("ID")) {
			commandId = this.command.optString("ID");
		}

		try {
			result = this.doAction();
		} catch (Exception err) {
			LOGGER.error("Exceute action {} error {}", this.command, err.getLocalizedMessage());

			RestfulResult<Object> rst = ClientSdk.createErrorResult(err.getLocalizedMessage(), 500, 500);
			result = rst.toJson();
		}

		if (result == null) {
			return;
		}

		if (commandId != null) {
			result.put("ID", commandId);
		}

		this.socket.sendToClient(result.toString());
	}

	private JSONObject doAction() {
		String userToken = command.optString("userToken");

		if (StringUtils.isBlank(userToken)) {
			RestfulResult<Object> rst = ClientSdk.createErrorResult("need_user_token", 0, 401);
			JSONObject returnJson = rst.toJson();
			return returnJson;
		}

		String restfulRoot = UPath.getInitPara("chat_restful_root");

		if (StringUtils.isBlank(restfulRoot)) {
			return UJSon.rstFalse("请在 ewa_conf.xml的 initPara中设置 chat_restful_root参数（访问restful的网址和前缀）");
		}

		this.client = new ClientSdk(restfulRoot, userToken);
		this.client.setFromIp(this.socket.getRv().s("SYS_REMOTEIP"));
		this.client.setFromUserAgent(this.socket.getRv().s("SYS_USER_AGENT"));

		// 提交的附加参数
		if (command.has("parameters")) {
			client.setParames(command.optString("parameters"));
		}

		if ("updateRoomName".equalsIgnoreCase(this.action)) {
			return this.actionUpdateRoomName();
		}

		// 获取我的房间列表
		if ("rooms".equalsIgnoreCase(this.action)) {
			return this.actionRooms();
		}
		// 添加房间成员
		if ("exitRoom".equalsIgnoreCase(this.action)) {
			return this.actionExitRoom();
		}
		// 删除房间成员
		if ("deleteRoomMembers".equalsIgnoreCase(this.action)) {
			return this.actionDeleteRoomMembers();
		}
		// 添加房间成员
		if ("addRoomMembers".equalsIgnoreCase(this.action)) {
			return this.actionAddRoomMembers();
		}
		// 获取房间成员
		if ("roomMembers".equalsIgnoreCase(this.action)) {
			return this.actionRoomMembers();
		}
		// 创建聊天室
		if ("createRoom".equalsIgnoreCase(this.action)) {
			return this.actionCreateRoom();
		}
		// 获取我的朋友或公司员工
		if ("friends".equalsIgnoreCase(this.action)) {
			return this.actionMyFriends();
		}
		// 提交新帖子
		if ("post".equalsIgnoreCase(this.action)) {
			return this.actionPost();
		}
		// 进入房间并获取列表
		if ("topics".equalsIgnoreCase(this.action)) {
			return this.actionTopics();
		}
		// 用户上传文件/图片后广播消息
		if ("uploaded".equalsIgnoreCase(this.action)) {
			return this.actionUploaded();
		}
		// 我的信息
		if ("myinfo".equalsIgnoreCase(this.action)) {
			return this.actionMyInfo();
		}

		if ("delete".equalsIgnoreCase(this.action)) {
			return this.actionDelete();
		}

		return notImplementsAction();
	}

	/**
	 * 更改房间名称
	 * 
	 * @return
	 */
	private JSONObject actionUpdateRoomName() {
		this.chatRoomId = command.getLong("chatRoomId");
		RestfulResult<Object> rst = client.updateRoomInfo(chatRoomId, command);
		String newRoomName = command.optJSONObject("body").optString("cht_rom_name");
		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		String userName = this.socket.getRv().s("cht_usr_name");

		// 更改群名的通知消息
		String notification = "\"" + userName + "\"{CHANGE_CHATROOM_NAME}\"" + newRoomName + "\"";
		// 通过服务器端提交通知
		RestfulResult<Object> rstServer = this.postSystemNotification(chatRoomId, notification);

		if (rstServer.isSuccess()) {
			JSONObject msg = (JSONObject) rstServer.getRawData();
			msg.put("notification", notification);
			// 广播这条消息
			this.boradRoomMessage(msg, CHAT_BROAD_ROOM_NAME_CHANGED, true);
		}
		JSONObject returnJson = rst.toJson();
		return returnJson;
	}

	/**
	 * 删除帖子
	 * 
	 * @return
	 */
	private JSONObject actionExitRoom() {
		this.chatRoomId = command.getLong("chatRoomId");
		RestfulResult<Object> rst = client.exitChatRoom(chatRoomId);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}
		JSONObject returnJson = rst.toJson();
		return returnJson;
	}

	/**
	 * 添加房间成员
	 * 
	 * @return
	 */
	private JSONObject actionAddRoomMembers() {
		this.chatRoomId = command.getLong("chatRoomId");
		// 添加的用户列表，用 ","分割的cht_usr_id
		String ids = this.command.optString("ids");
		String[] arrIds = StringUtils.split(ids, ",");
		List<Long> longIds = new ArrayList<>();

		for (int i = 0; i < arrIds.length; i++) {
			long userId = Long.parseLong(arrIds[i]);
			longIds.add(userId);
		}

		RestfulResult<Object> rst = client.addUserRoomMembers(chatRoomId, ids);
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		// 获取房间信息
		RestfulResult<Object> rst1 = client.getChatRoom(chatRoomId);
		if (!rst1.isSuccess()) {
			return rst1.toJson();
		}

		// 广播数据，返回的data数据
		JSONObject result = new JSONObject(rst1.getRawData().toString());

		// 广播这条消息
		this.boradMessage(result, CHAT_BROAD_ROOM_CREATED, longIds);

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	private JSONObject actionMyFriends() {
		Long relativeRoomId = null;
		if (this.command.has("relativeRoomId")) {
			// 关联在房间号码
			relativeRoomId = this.command.optLong("relativeRoomId");
		}
		RestfulResult<Object> rst = client.myFriends(relativeRoomId);
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	/**
	 * 删除房间成员
	 * 
	 * @return
	 */
	private JSONObject actionDeleteRoomMembers() {
		this.chatRoomId = command.getLong("chatRoomId");

		// 删除的成员 userId的字符串
		String ids = this.command.optString("ids");
		// 添加的用户列表，用 ","分割的cht_usr_id
		List<Long> longIds = this.parseIds(ids);

		RestfulResult<Object> rst = client.deleteChatRoomMembers(chatRoomId, ids);
		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject result = new JSONObject();
		result.put("cht_rom_id", chatRoomId);
		// 广播这条消息
		this.boradMessage(result, CHAT_BROAD_ROOM_KICKED, longIds);

		JSONObject returnJson = rst.toJson();
		return returnJson;

	}

	/**
	 * 获取房间成员
	 * 
	 * @return
	 */
	private JSONObject actionRoomMembers() {
		this.chatRoomId = command.getLong("chatRoomId");
		Integer limits = null;
		if (command.has("limits")) {
			limits = command.optInt("limits");
		}
		RestfulResult<Object> rst = client.getChatRoomMembers(chatRoomId, limits);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	private List<Long> parseIds(String ids) {
		// 添加的用户列表，用 ","分割的cht_usr_id
		String[] arrIds = StringUtils.split(ids, ",");
		List<Long> longIds = new ArrayList<>();

		for (int i = 0; i < arrIds.length; i++) {
			long userId = Long.parseLong(arrIds[i]);
			longIds.add(userId);
		}

		return longIds;

	}

	/**
	 * 创建房间
	 * 
	 * @return
	 */
	private JSONObject actionCreateRoom() {
		String ids = this.command.optString("ids");

		// 添加的用户列表，用 ","分割的cht_usr_id
		List<Long> longIds = this.parseIds(ids);

		RestfulResult<Object> rst = client.createUserRoom(ids);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		// 返回的data数据
		JSONObject result = new JSONObject(rst.getRawData().toString());

		// 广播这条消息
		this.boradMessage(result, CHAT_BROAD_ROOM_CREATED, longIds);

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	private JSONObject actionRooms() {
		String search = null;
		search = this.command.optString("search");

		RestfulResult<Object> rst = client.getChatRooms(search);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	/**
	 * 删除帖子
	 * 
	 * @return
	 */
	private JSONObject actionDelete() {
		this.chatRoomId = command.getLong("chatRoomId");
		long messageId = command.getLong("messageId");
		RestfulResult<Object> rst = client.deleteMessage(chatRoomId, messageId);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject postDelete = new JSONObject();
		postDelete.put("messageId", messageId);
		postDelete.put("chatRoomId", chatRoomId);

		// 广播这条消息
		this.boradRoomMessage(postDelete, CHAT_BROAD_DELETE_ID, false);

		JSONObject returnJson = rst.toJson();
		return returnJson;
	}

	/**
	 * 提交新帖子
	 * 
	 * @return
	 */
	private JSONObject actionPost() {
		this.chatRoomId = command.getLong("chatRoomId");
		ClientChatUserGroup.addUserToTopicGroup(this.chatRoomId, socket.getUnid());

		RestfulResult<Object> rst = client.newMessage(chatRoomId, command);

		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		// 返回的data数据
		JSONObject result = new JSONObject(rst.getRawData().toString());
		// 广播这条消息
		this.boradRoomMessage(result, CHAT_BROAD_MSG_ID, false);

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	private ServerSdk getServerSdk() throws Exception {
		RequestValue rv = socket.getRv().clone();
		if (rv.isBlank("cht_sup_id")) {
			throw new Exception("Not found User info");
		}
		int supId = rv.getInt("cht_sup_id");

		ServerSdk server = ServerSdk.getInstanceBySupId(supId);
		return server;
	}

	private RestfulResult<Object> postSystemNotification(long chatRoomId, String notification) {
		ServerSdk server = null;
		try {
			server = this.getServerSdk();
		} catch (Exception e) {
			return ClientSdk.createErrorResult(e.getMessage(), 500, 500);
		}

		return server.postSystemNotification(chatRoomId, notification);

	}

	/**
	 * 获取我的信息
	 * 
	 * @return
	 */
	private JSONObject actionMyInfo() {
		RestfulResult<Object> rst = client.myInfo();

		if (!rst.isSuccess()) {
			return rst.toJson();
		}
		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		JSONObject userJson = returnJson.optJSONObject("data");

		ChatUser user = new ChatUser();
		user.initValues(userJson);

		long userId = user.getChtUsrId();

		int supId = user.getChtUsrSupId();

		socket.getRv().addOrUpdateValue("cht_sup_id", supId);
		socket.getRv().addOrUpdateValue("cht_usr_name", user.getChtUsrName());
		socket.getRv().addOrUpdateValue("cht_usr_id", user.getChtUsrId());
		// 建立socket和用户之间的关系
		USER_MAP.put(userId, this.socket.getUnid());

		return returnJson;
	}

	/**
	 * 用户上传文件/图片后广播消息
	 * 
	 * @return
	 */
	private JSONObject actionUploaded() {
		this.chatRoomId = command.getLong("chatRoomId");
		String ref = command.optString("ref");
		String refId = command.optString("ref_id");

		RestfulResult<Object> rst = client.getChatRoomTopicUploaded(chatRoomId, ref, refId);
		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONArray msgs = (JSONArray) rst.getRawData();

		this.boradRoomMessage(msgs, CHAT_BROAD_MSG_ID, false);

		JSONObject ret = rst.toJson();
		// 数组转换未单个对象
		ret.put("data", msgs.getJSONObject(0));
		return ret;
	}

	/**
	 * 进入房间并获取列表
	 * 
	 * @return
	 */
	private JSONObject actionTopics() {
		this.chatRoomId = command.getLong("chatRoomId");

		Long lastTopicId = null;
		if (command.has("lastTopicId") && command.get("lastTopicId") != JSONObject.NULL) {
			lastTopicId = command.optLong("lastTopicId");

			if (lastTopicId == 0) {
				lastTopicId = null;
			}
		}
		// null 表示第一次进入房间
		if (lastTopicId == null) {
			// 添加活动用户进入房间组
			ClientChatUserGroup.addUserToTopicGroup(this.chatRoomId, socket.getUnid());
		}

		RestfulResult<Object> rst = client.getChatRoomTopics(chatRoomId, lastTopicId);
		// 原始数据
		if (!rst.isSuccess()) {
			return rst.toJson();
		}

		JSONObject returnJson = new JSONObject(rst.getReturnResult());
		return returnJson;
	}

	/**
	 * 未实现的方法
	 * 
	 * @return
	 */
	private JSONObject notImplementsAction() {
		RestfulResult<Object> rst = ClientSdk.createErrorResult("Not implements action: (" + this.action + ")", 405,
				405);
		JSONObject returnJson = rst.toJson();
		return returnJson;
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msgs        消息
	 * @param broadcastId 广播标记（客户端用）
	 * @param toUserIds   指定的用户列表(userId)
	 */
	private void boradMessage(JSONArray msgs, String broadcastId, List<Long> toUserIds) {
		JSONObject result = new JSONObject();
		// js用于处理广播消息的方法id
		result.put("BROADCAST_ID", broadcastId);

		for (int i = 0; i < msgs.length(); i++) {
			JSONObject item = msgs.getJSONObject(i);
			item.put("_msg_from_", this.socket.getUnid());
		}
		result.put("LIST", msgs);
		String broadcast = result.toString();

		toUserIds.forEach(userId -> {
			if (USER_MAP.containsKey(userId)) {
				String socketUnid = USER_MAP.get(userId);
				EwaWebSocketBus ws = EwaWebSocketContainer.getSocketByUnid(socketUnid);
				if (ws != null) {
					ws.sendToClient(broadcast);
				}
			}
		});
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msg         消息
	 * @param broadcastId 广播标记（客户端用）
	 * @param toUserIds   指定的用户列表(userId)
	 */
	private void boradMessage(JSONObject msg, String broadcastId, List<Long> toUserIds) {

		JSONArray msgs = new JSONArray();
		msgs.put(msg);

		this.boradMessage(msgs, broadcastId, toUserIds);
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msg           消息
	 * @param broadcastId   广播标记（客户端用）
	 * @param includeMyself 包括自己
	 */
	private void boradRoomMessage(JSONObject msg, String broadcastId, boolean includeMyself) {
		JSONArray msgs = new JSONArray();
		msgs.put(msg);
		this.boradRoomMessage(msgs, broadcastId, includeMyself);
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msgs          消息
	 * @param broadcastId   广播标记（客户端用）
	 * @param includeMyself 包括自己
	 */
	private void boradRoomMessage(JSONArray msgs, String broadcastId, boolean includeMyself) {
		if (msgs.length() == 0) {
			return;
		}

		Map<String, Boolean> map = ClientChatUserGroup.getGroup(this.chatRoomId);
		if (map == null || map.size() == 0) {
			return;
		}

		for (String key : map.keySet()) {
			// 从真正的容器中找到socket
			EwaWebSocketBus socket = EwaWebSocketContainer.getSocketByUnid(key);
			if (socket == null) {
				map.remove(key);
				continue;
			}

			if (!includeMyself && key.equals(this.socket.getUnid())) {
				continue;
			}

			JSONObject result = new JSONObject();

			// js用于处理广播消息的方法id
			result.put("BROADCAST_ID", broadcastId);
			// 来源
			result.put("MSG_FROM", this.socket.getUnid());
			// 数据
			result.put("LIST", msgs);
			socket.sendToClient(result.toString());
		}
	}

	/**
	 * 获取Rv，因为Socket是长连接，因此RequetValue在首次连接就固定下来，<br>
	 * 因此每次操作需要将Socket带的Rv克隆出来，并附加command参数
	 * 
	 * @return
	 */
	public RequestValue cloneRv() {
		RequestValue rv_clone = this.socket.getRv().clone();
		Iterator<?> it = this.command.keys();
		while (it.hasNext()) {
			String key = it.next().toString();
			String val = this.command.optString(key);
			rv_clone.getPageValues().remove(key);
			rv_clone.addValue(key, val);
		}

		rv_clone.resetDateTime();
		rv_clone.resetSysUnid();
		return rv_clone;
	}

	@Override
	public String getMethod() {
		return METHOD;
	}

}
