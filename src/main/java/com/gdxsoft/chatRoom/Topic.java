package com.gdxsoft.chatRoom;

import com.gdxsoft.chatRoom.dao.ChatCnt;
import com.gdxsoft.chatRoom.dao.ChatTopic;

import io.swagger.annotations.ApiModel;

@ApiModel(value = "topic", description = "组合 ChatTopic和ChatCnt 映射类")
public class Topic {

	private ChatTopic chatTopic;
	private ChatCnt chatCnt;

	public ChatTopic getChatTopic() {
		return chatTopic;
	}

	public void setChatTopic(ChatTopic chatTopic) {
		this.chatTopic = chatTopic;
	}

	public ChatCnt getChatCnt() {
		return chatCnt;
	}

	public void setChatCnt(ChatCnt chatCnt) {
		this.chatCnt = chatCnt;
	}
}
