package com.spark.dating.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ChatMessageSendResponse {
	private Long cmNo;
	private String cmMessage;
	private String cmDate;
	private boolean cmSendUserFlag;

	public static ChatMessageSendResponse from(ChatMessage send, Long opponentMno) {
		return ChatMessageSendResponse.builder()
				.cmNo(send.getCmNo())
				.cmMessage(send.getCmMessage())
				.cmSendUserFlag(opponentMno == send.getCmSendMember() ? true : false)
				.cmSendUserFlag(false)
				.cmDate(send.getCmDate().toString()).build();
	}

}
