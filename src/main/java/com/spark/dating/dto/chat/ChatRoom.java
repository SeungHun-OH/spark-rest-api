package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ChatRoom {

	private int cr_no;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int cr_matchingNo;
	private String cr_name;
	private int cr_age;
	private LocalDateTime cr_lastMessageDate;
	private String cr_lastMessage;

	@Builder
	public ChatRoom(int chatRoomId, String chatRoomName) {
		this.cr_no = chatRoomId;
		this.cr_name = chatRoomName;
	}

	public ChatRoom(int cr_no, String cr_name, int cr_age, String cr_lastMessage, LocalDateTime cr_lastMessageDate) {
		super();
		this.cr_no = cr_no;
		this.cr_name = cr_name;
		this.cr_age = cr_age;
		this.cr_lastMessage = cr_lastMessage;
		this.cr_lastMessageDate = cr_lastMessageDate;
	}

}
