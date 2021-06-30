package com.gdxsoft.chatRoom.sdk;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import com.gdxsoft.easyweb.script.servlets.RestfulResult;
import com.gdxsoft.easyweb.utils.UNet;
import com.gdxsoft.easyweb.utils.UUrl;
import com.gdxsoft.easyweb.utils.Utils;

public class ClientSdk {
	/**
	 * 创建 错误提示信息
	 * 
	 * @param errorMsg
	 * @param statusCode
	 * @return
	 */
	public static RestfulResult<Object> createErrorResult(String errorMsg, int errorCode, int httpStatusCode) {
		RestfulResult<Object> rr = new RestfulResult<>();
		rr.setCode(errorCode);
		rr.setHttpStatusCode(httpStatusCode);
		rr.setSuccess(false);
		rr.setMessage(errorMsg);
		rr.setRawData(rr.toJson());
		rr.setData(rr.toJson());

		return rr;
	}

	private String restfulRoot;
	private String userToken;
	private String parames;
	private String fromIp; // 客户端来源地址 ipv4/ipv6
	private String fromUserAgent; // 客户端浏览器UA

	public ClientSdk(String restfulRoot, String userToken) {
		this.restfulRoot = restfulRoot;
		this.userToken = userToken;
	}

	/**
	 * 获取我的好友关系
	 * 
	 * @param relativeRoomId 关联在房间号码
	 * @return
	 */
	public RestfulResult<Object> myFriends(Long relativeRoomId) {
		String path = "/friends";
		String url = this.createUrl(path);
		if (relativeRoomId != null) {
			url += "?relativeRoomId=" + relativeRoomId;
		}
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	public RestfulResult<Object> newMessage(long chatRomId, JSONObject msg) {
		String path = "/chatRooms/" + chatRomId + "/topics";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doPost(url, msg.optJSONObject("body").toString());

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 删除房间帖子内容
	 * 
	 * @param chatRomId 房间号
	 * @param messageId 帖子号码
	 * @return
	 */
	public RestfulResult<Object> deleteMessage(long chatRomId, long messageId) {
		String path = "/chatRooms/" + chatRomId + "/topics/" + messageId;
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doDelete(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		if (net.getLastStatusCode() == 200 || net.getLastStatusCode() == 204) {
			rr.setSuccess(true);
			rr.setHttpStatusCode(net.getLastStatusCode());
		}

		return rr;
	}

	/**
	 * 附加查询参数
	 * 
	 * @param url
	 * @return
	 */
	private String attacheParameters(String url) {
		if (StringUtils.isBlank(this.parames)) {
			return url;
		}

		if (url.indexOf("?") > 0) {
			url += "&" + this.parames;
		} else {
			url += "?" + this.parames;
		}

		return url;
	}

	/**
	 * 获取房间的帖子内容
	 * 
	 * @param chatRomId   房间号
	 * @param lastTopicId 最后一个帖子号码
	 * @return
	 */
	public RestfulResult<Object> getChatRoomTopics(long chatRomId, Long lastTopicId) {
		String path = "/chatRooms/" + chatRomId + "/topics";
		String url = this.createUrl(path);
		if (lastTopicId != null) {
			url += "?lastTopicId=" + lastTopicId;
		}

		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	public RestfulResult<Object> getChatRoomMembers(long chatRomId, Integer limits) {
		String path = "/chatRooms/" + chatRomId + "/members";
		String url = this.createUrl(path);
		if (limits != null && limits > 0) {
			url += "?ewa_pagesize=" + limits;
		}
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 退出房间
	 * 
	 * @param chatRomId
	 * @return
	 */
	public RestfulResult<Object> exitChatRoom(long chatRomId) {
		String path = "/chatRooms/" + chatRomId + "/myAcl";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doDelete(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	public RestfulResult<Object> notification(long chatRomId, String msg) {
		JSONObject notication = new JSONObject();
		// cht_cnt: "notification"
		// cht_rom_id: "64748967224672256"
		// cht_type: "text"
		// cht_usr_id
		notication.append("cht_cnt", msg);
		notication.append("cht_type", "notication");

		return this.newMessage(chatRomId, notication);
	}

	public RestfulResult<Object> getChatRoom(long chatRoomId) {
		String path = "/chatRooms/" + chatRoomId;
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 跟新房间信息
	 * 
	 * @param chatRoomId 房间号
	 * @param msg        更新的内容 cht_rom_name,cht_rom_memo,cht_rom_owner
	 * @return
	 */
	public RestfulResult<Object> updateRoomInfo(long chatRoomId, JSONObject msg) {
		String path = "/chatRooms/" + chatRoomId;
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doPut(url, msg.getJSONObject("body").toString());

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;

	}

	/**
	 * 获取房间
	 * 
	 * @param chatRomId 房间号
	 * @param search    房间名称查询
	 * @return
	 */
	public RestfulResult<Object> getChatRooms(String search) {
		String path = "/chatRooms/";
		String url = this.createUrl(path);
		if (StringUtils.isNotBlank(search)) {
			UUrl uu = new UUrl(url);
			uu.add("ewa_search", "cht_rom_name[lk]" + search);
			url = uu.getUrlWithDomain();
		}

		url = this.attacheParameters(url);

		UNet net = this.createNet();
		net.setIsShowLog(true);
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 添加用户到房间里
	 * 
	 * @param ids
	 * @return
	 */
	public RestfulResult<Object> addUserRoomMembers(long chatRoomId, String ids) {
		String path = "/chatRooms/" + chatRoomId + "/members";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		JSONObject body = new JSONObject();
		body.put("users_id_split", ids);

		String bodyContent = body.toString();

		UNet net = this.createNet();
		net.setIsShowLog(true);
		String rst = net.postMsg(url, bodyContent);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 删除房间成员
	 * 
	 * @param chatRoomId
	 * @param deleteUserIds 删除的成员 userId的字符串
	 * @return
	 */
	public RestfulResult<Object> deleteChatRoomMembers(long chatRoomId, String deleteUserIds) {
		String path = "/chatRooms/" + chatRoomId + "/members";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		JSONObject body = new JSONObject();
		body.put("users_id_split", deleteUserIds);

		String bodyContent = body.toString();

		UNet net = this.createNet();
		net.setIsShowLog(true);
		String rst = net.doDelete(url, bodyContent);

		RestfulResult<Object> rr = new RestfulResult<>();

		if (rst == null) {
			rr.setHttpStatusCode(net.getLastStatusCode());
		} else {
			rr.parse(rst);
		}

		return rr;
	}

	/**
	 * 用户创建房间
	 * 
	 * @param ids
	 * @return
	 */
	public RestfulResult<Object> createUserRoom(String ids) {
		String path = "/chatRooms/";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		JSONObject body = new JSONObject();
		body.put("users_id_split", ids);
		body.put("cht_rom_name", "");
		body.put("cht_rom_name_en", "");
		body.put("cht_rom_type", "");
		body.put("cht_rom_ref", "user");
		body.put("cht_rom_ref_id", Utils.getGuid());

		String bodyContent = body.toString();

		UNet net = this.createNet();
		net.setIsShowLog(true);
		String rst = net.postMsg(url, bodyContent);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 获取房间的帖子内容
	 * 
	 * @param chatRomId   房间号
	 * @param lastTopicId 最后一个帖子号码
	 * @return
	 */
	public RestfulResult<Object> getChatRoomTopicUploaded(long chatRomId, String uploadRef, String uploadRefId) {
		String path = "/chatRooms/" + chatRomId + "/topics";
		String url = this.createUrl(path);
		url += "?ref=" + uploadRef + "&Ref_Id=" + uploadRefId;
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	/**
	 * 获取我的信息
	 * 
	 * @return
	 */
	public RestfulResult<Object> myInfo() {
		String path = "/chatUsers/myself";
		String url = this.createUrl(path);
		url = this.attacheParameters(url);

		UNet net = this.createNet();
		String rst = net.doGet(url);

		RestfulResult<Object> rr = new RestfulResult<>();
		rr.parse(rst);

		return rr;
	}

	private String createUrl(String path) {
		String url = this.restfulRoot + path;
		return url;
	}

	public UNet createNet() {
		UNet net = new UNet();
		net.addHeader("Authorization", this.userToken);
		net.setIsShowLog(false);
		if (StringUtils.isNotBlank(fromIp)) { // 客户端来源地址
			net.addHeader("X-Forwarded-For", fromIp);
		}
		if (StringUtils.isNotBlank(this.fromUserAgent)) { // 客户端浏览器UA
			net.setUserAgent(fromUserAgent);
		}
		return net;
	}

	public String getParames() {
		return parames;
	}

	public void setParames(String parames) {
		this.parames = parames;
	}

	/**
	 * 客户端来源地址 ipv4/ipv6
	 * 
	 * @return IP地址 (ipv4/ipv6)
	 */
	public String getFromIp() {
		return fromIp;
	}

	/**
	 * 客户端来源地址 ipv4/ipv6
	 * 
	 * @param fromIp IP地址 (ipv4/ipv6)
	 */
	public void setFromIp(String fromIp) {
		this.fromIp = fromIp;
	}

	/**
	 * 客户端浏览器UA
	 * 
	 * @return UserAgent
	 */
	public String getFromUserAgent() {
		return fromUserAgent;
	}

	/**
	 * 客户端浏览器UA
	 * 
	 * @param fromUserAgent 览器UserAgent
	 */
	public void setFromUserAgent(String fromUserAgent) {
		this.fromUserAgent = fromUserAgent;
	}

}
