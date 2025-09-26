package com.spark.dating.dto.chat;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class MatchingRoomMapping {

	private Long mcMatchingNo;
	private Long mcChatRoomNo;
}
