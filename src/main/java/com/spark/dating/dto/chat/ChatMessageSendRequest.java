package com.spark.dating.dto.chat;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ChatMessageSendRequest {

	private String cmNo;
	
	@NotBlank(message = "메세지를 입력해주세요.")
	private String cmMessage;
	
}
