package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomSelectRequest {

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private int crNo;
	private String crName;
	private int crAge;
	private String crLastMessage;
	private LocalDateTime crLastMessageDate;

}
