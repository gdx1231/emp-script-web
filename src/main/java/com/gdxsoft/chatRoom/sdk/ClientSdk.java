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
		String rst = net.postMsg(url, msg.toString());

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
