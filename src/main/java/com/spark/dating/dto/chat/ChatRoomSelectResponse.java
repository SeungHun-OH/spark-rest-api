package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class ChatRoomSelectResponse {


	private String encodedUUID;
	private String chatRoomName;
	private String opponentUuid;
	private String lastMessage;
	private Integer age;
	private String status;
	private Integer unreadCount;
	private String attachNo;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime date;

	
	
}
