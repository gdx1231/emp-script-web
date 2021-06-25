package com.gdxsoft.chatRoom.sdk;

import org.json.JSONObject;

import com.gdxsoft.easyweb.script.servlets.RestfulResult;
import com.gdxsoft.easyweb.utils.UNet;

public class ClientSdk {
	private String restfulRoot;
	private String userToken;

	public ClientSdk(String restfulRoot, String userToken) {
		this.restfulRoot = restfulRoot;
		this.userToken = userToken;
	}

	public RestfulResult<Object> newMessages(long chatRomId, JSONObject msg) {
		String path = "/chatRooms/" + chatRomId + "/topics";
		String url = this.createUrl(path);
		UNet net = this.createNet();
		String rst = net.postMsg(url, msg.optJSONObject("body").toString());

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
	public RestfulResult<Object> getChatRoomTopics(long chatRomId, Long lastTopicId) {
		String path = "/chatRooms/" + chatRomId + "/topics";
		String url = this.createUrl(path);
		if (lastTopicId != null) {
			url += "?lastTopicId=" + lastTopicId;
		}
		UNet net = this.createNet();
		String rst = net.doGet(url);

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

		return net;
	}
}
