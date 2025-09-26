package com.spark.dating.dto.chat;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessage {

	private long cmNo;
	private long cmSendUser;
	private int cmChatRoomNo;
	private String cmSendUserName;
}
