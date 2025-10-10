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
//	private boolean cmSendUserFlag;
	private String cmSendMemberUuid;

	public static ChatMessageSendResponse from(ChatMessage send, String sendMemberUuid) {
		return ChatMessageSendResponse.builder()
				.cmNo(send.getCmNo())
				.cmMessage(send.getCmMessage())
//				.cmSendUserFlag(opponentMno == send.getCmSendMember() ? true : false)
//				.cmSendUserFlag(false)
				.cmSendMemberUuid(sendMemberUuid)
				.cmDate(send.getCmDate().toString()).build();
	}

}
