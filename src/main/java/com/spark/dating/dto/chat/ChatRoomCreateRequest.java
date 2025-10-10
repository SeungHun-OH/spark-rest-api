package com.spark.dating.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatRoomCreateRequest {

	private Long matchingNo;
	private Long memberNo;
	private String chatRoomUUID;
	private long chatRoomNo;

}
