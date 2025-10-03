package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class ChatMessage {

	private Long cmNo;

	private Long cmSendMember;

	private Long cmChatRoomNo;

	private String cmMessage;
	
	@Builder.Default
	private LocalDateTime cmDate = LocalDateTime.now();

	
}
