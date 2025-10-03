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
	private String cmNo;
	private String cmMessage;
	private String cmDate;
	@Builder.Default
	private boolean cmSendUserFlag = false;

	public static ChatMessageSendResponse from(ChatMessage send, ChatMessageSendRequest request) {
		return ChatMessageSendResponse.builder()
				.cmNo(request.getCmNo())
				.cmMessage(send.getCmMessage())
				.cmDate(send.getCmDate().toString()).build();
	}

}
