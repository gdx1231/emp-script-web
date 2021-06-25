package com.gdxsoft.chatRoom.sdk;

import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	public final static String METHOD = "chat";

	// 推送帖子列表消息标志
	public final static String CHAT_BROAD_MSG_ID = "chat_broad_msg";
	// 广播删除帖子
	public final static String CHAT_BROAD_DELETE_ID = "chat_broad_delete_it";

	private EwaWebSocket socket;
	private JSONObject command;
	private String action;

	private long chatRoomId;

	/**
	 * 初始化对象
	 * 
	 * @param socket  EwaWebSocket
	 * @param command 调用的命令
	 */
	public ClientChatWsImpl(EwaWebSocket socket, JSONObject command) {
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
			result = UJSon.rstFalse(err.getLocalizedMessage());
			LOGGER.error(err.getLocalizedMessage());
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

		// 首先用户要提交user_token
		if ("user_token".equals(this.action)) {
			String userToken = command.optString("user_token");
			this.socket.getRv().addOrUpdateValue("user_token", userToken);
			// 获取我的信息
			this.action = "myinfo";
			return this.doAction();
		}

		String userToken = this.socket.getRv().s("user_token");
		if (StringUtils.isBlank(userToken)) {
			return UJSon.rstFalse("请先提交用户token");
		}

		String restfulRoot = UPath.getInitPara("chat_restful_root");

		if (StringUtils.isBlank(restfulRoot)) {
			return UJSon.rstFalse("请在 ewa_conf.xml的 initPara中设置 chat_restful_root参数（访问restful的网址和前缀）");
		}

		ClientSdk client = new ClientSdk(restfulRoot, userToken);

		// 提交新帖子
		if ("post".equals(this.action)) {
			this.chatRoomId = command.getLong("chatRoomId");
			ClientChatUserGroup.addUserToTopicGroup(this.chatRoomId, socket.getUnid());

			RestfulResult<Object> rst = client.newMessages(chatRoomId, command);

			// 原始数据
			if (!rst.isSuccess()) {
				return UJSon.rstFalse(rst.getMessage());
			}

			// 返回的data数据
			JSONObject result = new JSONObject(rst.getRawData().toString());

			JSONArray arr = new JSONArray();
			arr.put(new JSONObject(result.toString()));

			// 广播这条消息
			this.boradMessage(arr, CHAT_BROAD_MSG_ID, false);

			JSONObject returnJson = new JSONObject(rst.getReturnResult());
			return returnJson;
		}
		// 进入房间并获取列表
		if ("topics".equals(this.action)) {
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
				ClientChatUserGroup.addUserToTopicGroup(this.chatRoomId, socket.getUnid());
			}

			RestfulResult<Object> rst = client.getChatRoomTopics(chatRoomId, lastTopicId);
			// 原始数据
			if (!rst.isSuccess()) {
				return UJSon.rstFalse(rst.getMessage());
			}

			JSONObject returnJson = new JSONObject(rst.getReturnResult());
			return returnJson;
		}
		// 进入房间并获取列表
		if ("uploaded".equals(this.action)) {
			this.chatRoomId = command.getLong("chatRoomId");
			String ref = command.optString("ref");
			String refId = command.optString("ref_id");

			RestfulResult<Object> rst = client.getChatRoomTopicUploaded(chatRoomId, ref, refId);
			// 原始数据
			if (!rst.isSuccess()) {
				return UJSon.rstFalse(rst.getMessage());
			}
			JSONArray msgs = (JSONArray) rst.getRawData();
			this.boradMessage(msgs, CHAT_BROAD_MSG_ID, false);

			return null;
		}
		// 我的信息
		if ("myinfo".equals(this.action)) {

			RestfulResult<Object> rst = client.myInfo();

			if (!rst.isSuccess()) {
				return UJSon.rstFalse(rst.getMessage());
			}
			JSONObject returnJson = new JSONObject(rst.getReturnResult());
			return returnJson;
		}

		return notImplementsAction();
	}

	/**
	 * 未实现的方法
	 * 
	 * @return
	 */
	private JSONObject notImplementsAction() {
		JSONObject result = new JSONObject();
		result.put("RST", false);
		result.put("ERR", "Not implements action: (" + this.action + ")");

		return result;
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msgs          消息
	 * @param broadcastId   广播标记（客户端用）
	 * @param includeMyself 包括自己
	 */
	private void boradMessage(JSONArray msgs, String broadcastId, boolean includeMyself) {
		if (msgs.length() == 0) {
			return;
		}

		Map<String, Boolean> map = ClientChatUserGroup.getGroup(this.chatRoomId);
		if (map == null || map.size() == 0) {
			return;
		}

		for (String key : map.keySet()) {
			// 从真正的容器中找到socket
			EwaWebSocket socket = EwaWebSocketContainer.getSocketByUnid(key);
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

			for (int i = 0; i < msgs.length(); i++) {
				JSONObject item = msgs.getJSONObject(i);
				item.put("_", "由 ClientChatWsImpl 创建");
			}

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
