package com.spark.dating.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ChatRoomSelectRequest {


	private String encodedUUID;
	private String chatRoomName;
	private String lastMessage;
	private Integer age;
	private String date;

	
	
}
