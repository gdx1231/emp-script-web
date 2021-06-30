package com.gdxsoft.chatRoom.sdk;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gdxsoft.api.Auth;
import com.gdxsoft.chatRoom.dao.ChatUser;
import com.gdxsoft.easyweb.script.servlets.RestfulResult;
import com.gdxsoft.easyweb.utils.UNet;
import com.gdxsoft.easyweb.utils.UPath;
import com.gdxsoft.web.dao.WebUser;
import com.gdxsoft.web.dao.WebUserDao;

public class ServerSdk {
	private static Map<String, Auth> AUTHS = new ConcurrentHashMap<>();

	public static ServerSdk getInstanceBySupId(int supId) throws Exception {
		String restfulRoot = UPath.getInitPara("chat_restful_server_root");
		if (StringUtils.isBlank(restfulRoot)) {
			throw new Exception("请在 ewa_conf.xml的 initPara中设置 chat_restful_server_root参数（访问restful的网址和前缀）");
		}

		return getInstanceBySupId(supId, restfulRoot);

	}

	public static ServerSdk getInstanceBySupId(int supId, String restfulRoot) throws Exception {
		String key = supId + "///" + restfulRoot;
		Auth auth = null;
		ServerSdk server = new ServerSdk(restfulRoot);

		if (AUTHS.containsKey(key)) {
			auth = AUTHS.get(key);
			if (auth.getEndTime() > System.currentTimeMillis()) { // 未过期
				server.serverToken = auth.getJwtToken();
				return server;
			}
		}

		auth = new Auth();
		if (auth.createJwtToken(supId)) {
			// 放到缓存中
			AUTHS.put(key, auth);
			server.serverToken = auth.getJwtToken();
			return server;
		} else {
			throw new Exception(auth.getErrorMessage());
		}

	}

	private String errorMessage;
	private String result;
	private int httpStatusCode;
	private String apiRoot;
	private String serverToken;
	private String databaseName;

	public ServerSdk(String apiRoot) {
		this.apiRoot = apiRoot;
	}

	/**
	 * 发布系统通知
	 * 
	 * @param chatRoomId
	 * @param notification
	 * @return
	 */
	public RestfulResult<Object> postSystemNotification(long chatRoomId, String notification) {
		long userId = 0; // 系统消息
		String messageType = "notification";
		RestfulResult<Object> rr = this.postMessage(chatRoomId, userId, notification, messageType);

		return rr;
	}

	/**
	 * 发布消息
	 * 
	 * @param chatRoomId
	 * @param message
	 * @param messageType
	 * @return
	 */
	public RestfulResult<Object> postMessage(long chatRoomId, long userId, String message, String messageType) {
		JSONObject body = new JSONObject();
		body.put("cht_cnt", message);
		body.put("cht_type", messageType);
		body.put("cht_usr_id", userId);

		UNet net = getNet();
		String url = getApiPath("chatRooms/" + chatRoomId+"/topics");
		String result = net.postMsg(url, body.toString());
		// System.out.println(result);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		return rr;
	}

	public int getHttpStatusCode() {
		return this.httpStatusCode;
	}

	public String getResult() {
		return this.result;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public String getApiPath(String path) {
		return this.apiRoot + (this.apiRoot.endsWith("/") ? "" : "/") + path;
	}

	public String getSuperToken() {

		return serverToken;

	}

	public UNet getNet() {
		UNet net = new UNet();

		net.addHeader("Authorization", "Bearer " + getSuperToken());
		net.setIsShowLog(false);

		return net;
	}

	public String createUserToken(long chatUserId) {

		UNet net = getNet();
		String url = getApiPath("chatUsers/" + chatUserId + "/tokens");
		String result = net.postMsg(url, "{}");

		System.out.println(result);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		if (!rr.isSuccess()) {
			this.errorMessage = rr.getMessage();
			System.out.println(rr.getMessage());
			return null;
		}

		JSONObject obj = (JSONObject) rr.getRawData();

		return obj.optString("cht_token");

	}

	public long addUserToServer(int userId) {
		ChatUser chatUser = new ChatUser();
		WebUserDao d = new WebUserDao();
		WebUser user = d.getRecord(userId);
		if (user == null) {
			return -1;
		}

		chatUser.setChtUsrName(user.getUsrName());

		String gender = "U";
		if ("F".equalsIgnoreCase(user.getUsrSex()) || "M".equalsIgnoreCase(user.getUsrSex())) {
			gender = user.getUsrSex();
		}
		chatUser.setChtUsrGender(gender);
		chatUser.setChtUsrMobile(user.getUsrMobile());
		chatUser.setChtUsrRef("web_user.usr_id");
		chatUser.setChtUsrRefId(userId + "");

		String body = chatUser.toJSON().toString();
		System.out.println(body);

		UNet net = getNet();
		String url = getApiPath("chatUsers");
		String result = net.postMsg(url, body);

		System.out.println(result);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		if (!rr.isSuccess()) {
			this.errorMessage = rr.getMessage();
			System.out.println(rr.getMessage());
			return -1;
		}

		JSONObject obj = (JSONObject) rr.getRawData();
		chatUser.initValues(obj);

		return chatUser.getChtUsrId();
	}

	public long checkChatUser(int userId) {
		UNet net = getNet();

		String url = getApiPath("chatUsers");
		url += "?cht_usr_ref=web_user.usr_id&cht_usr_ref_id=" + userId;

		String result = net.doGet(url);
		int code = net.getLastStatusCode();

		this.result = result;
		this.httpStatusCode = code;
		System.out.println(result);
		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		if (!rr.isSuccess()) {
			this.errorMessage = rr.getMessage();
			System.out.println(rr.getMessage());
			return -1;
		}

		if (rr.getRecordCount() == 0) {
			return 0;
		}

		JSONArray arr = (JSONArray) rr.getRawData();
		JSONObject user = arr.getJSONObject(0);

		return user.optLong("cht_usr_id");
	}

	public long addRoom(long chatUserId, String roomType, String roomName, String roomNameEn) {
		UNet net = getNet();
		String url = getApiPath("chatRooms");

		JSONObject body = new JSONObject();
		body.put("cht_rom_creator", chatUserId);
		body.put("cht_rom_owner", 0);
		body.put("cht_rom_type", roomType);
		body.put("cht_rom_ref", roomType);
		body.put("cht_rom_ref_id", chatUserId);
		body.put("cht_rom_name", roomName == null ? "" : roomName);
		body.put("cht_rom_name_en", roomNameEn == null ? "" : roomNameEn);

		System.out.println(body);

		String result = net.postMsg(url, body.toString());
		System.out.println(result);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		if (!rr.isSuccess()) {
			this.errorMessage = rr.getMessage();
			System.out.println(rr.getMessage());
			return -1;
		}

		JSONObject obj = (JSONObject) rr.getRawData();

		return obj.getLong("cht_rom_id");
	}

	public long checkRoomSystem(long chatUserId, String roomType) {
		UNet net = getNet();
		String url = getApiPath("chatRooms");
		url += "?EWA_IS_SPLIT_PAGE=no&ref=" + roomType + "&ref_id=" + chatUserId + "&cht_usr_id=" + chatUserId;

		String result = net.doGet(url);
		int code = net.getLastStatusCode();

		this.result = result;
		this.httpStatusCode = code;
		System.out.println(result);
		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(result);

		if (!rr.isSuccess()) {
			this.errorMessage = rr.getMessage();
			System.out.println(rr.getMessage());
			return -1;
		}

		if (rr.getRecordCount() == 0) {
			return 0;
		}

		JSONArray arr = (JSONArray) rr.getRawData();
		JSONObject room = arr.getJSONObject(0);

		return room.optLong("cht_rom_id");
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
}
