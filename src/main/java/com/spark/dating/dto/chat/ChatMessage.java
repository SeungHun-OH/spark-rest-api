package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatMessage {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int cm_no;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int cm_senduser;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int cm_chatlistno;
	private String cm_sendusername;
	private String cm_msg;
	private LocalDateTime cm_date = LocalDateTime.now();

	public ChatMessage(String cm_sendusername, String cm_msg, LocalDateTime cm_date) {
		this.cm_sendusername = cm_sendusername;
		this.cm_msg = cm_msg;
		this.cm_date = cm_date;
	}

	@Builder
	public ChatMessage(int sendUser, int chatRoomNo, String message, String sendUserName) {
		this.cm_senduser = sendUser;
		this.cm_chatlistno = chatRoomNo;
		this.cm_msg = message;
		this.cm_sendusername = sendUserName;
	}

	public static ChatMessage createChatMessage(int senduser, int chatRoomNo, String message) {
		return ChatMessage.builder().sendUser(senduser).chatRoomNo(chatRoomNo).message(message).build();
	}

}
