package com.gdxsoft.chatRoom.sdk;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gdxsoft.easyweb.script.RequestValue;
import com.gdxsoft.easyweb.script.servlets.RestfulResult;
import com.gdxsoft.easyweb.utils.UJSon;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.websocket.IHandleMsg;
import com.gdxsoft.web.websocket.IndexWebSocket;
import com.gdxsoft.web.websocket.IndexWebSocketContainer;

/**
 * WebScocket 话题消息的处理
 */
public class ClientChatWsImpl extends Thread implements IHandleMsg {
	private static ConcurrentHashMap<Long, ConcurrentHashMap<String, Boolean>> TOPIC_USERS = new ConcurrentHashMap<>();
	private static Logger LOGGER = LoggerFactory.getLogger(ClientChatWsImpl.class);
	public final static String METHOD = "chat";

	// 推送帖子列表消息标志
	public final static String CHAT_BROAD_MSG_ID = "chat_broad_msg";
	// 广播删除帖子
	public final static String CHAT_BROAD_DELETE_ID = "chat_broad_delete_it";
	private IndexWebSocket socket;
	private JSONObject command;
	private String action;

	private long chnId;
	private String userToken;

	/**
	 * 初始化对象
	 * 
	 * @param socket  IndexWebSocket
	 * @param command 调用的命令
	 */
	public ClientChatWsImpl(IndexWebSocket socket, JSONObject command) {
		this.socket = socket;
		this.command = command;
		this.action = command.optString("ACTION");
		if (this.action == null) {
			this.action = "";
		}
	}

	@Override
	public void run() {
		JSONObject result = null;
		try {
			result = this.doAction();
		} catch (Exception err) {
			result = UJSon.rstFalse(err.getLocalizedMessage());
			LOGGER.error(err.getLocalizedMessage());
		}
		this.socket.sendToClient(result.toString());
	}

	private JSONObject doAction() {
		JSONObject result = null;

		// 首先用户要提交user_token
		if ("user_token".equals(this.action)) {
			this.userToken = command.optString("user_token");
			return UJSon.rstTrue(this.userToken);
		}

		if (StringUtils.isBlank(this.userToken)) {
			return UJSon.rstFalse("请先提交用户token");
		}

		String restfulRoot = UPath.getInitPara("chat_restful_root");

		if (StringUtils.isBlank(restfulRoot)) {
			return UJSon.rstFalse("请在 ewa_conf.xml的 initPara中设置 chat_restful_root参数（访问restful的网址和前缀）");
		}

		ClientSdk client = new ClientSdk(restfulRoot, this.userToken);

		// 提交新帖子
		if ("post".equals(this.action)) {
			RestfulResult<Object> rst = client.newMessages(chnId, command);
			result = new JSONObject(rst.getRawData());
			if (!rst.isSuccess()) {
				result.put("RST", false);
				return result;
			}
			JSONArray arr = new JSONArray();
			arr.put(new JSONObject(rst.getData().toString()));
			this.boradMessage(arr, CHAT_BROAD_MSG_ID);

			return UJSon.rstTrue(null);
		}

		result = new JSONObject();
		result.put("RST", false);
		result.put("ERR", "Unknowed action: (" + this.action + ")");
		return result;
	}

	/**
	 * 广播到所有客户端消息列表
	 * 
	 * @param msgs        消息
	 * @param broadcastId 广播标记（客户端用）
	 */
	private void boradMessage(JSONArray msgs, String broadcastId) {
		if (msgs.length() == 0) {
			return;
		}

		ConcurrentHashMap<String, Boolean> map = TOPIC_USERS.get(this.chnId);
		for (String key : map.keySet()) {
			// 从真正的容器中找到socket
			IndexWebSocket socket = IndexWebSocketContainer.getSocketByUnid(key);
			if (socket == null) {
				map.remove(key);
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
