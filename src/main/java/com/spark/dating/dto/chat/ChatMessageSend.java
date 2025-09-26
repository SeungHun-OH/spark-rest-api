package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageSend {

	private long cmNo;
	
	@NotNull(message = "사용자 번호는 필수입니다.")
	@Min(value = 1, message = "올바른 사용자 번호가 아닙니다.")
	@Positive(message = "올바른 사용자 번호가 아닙니다.")
	private long cmSendUser;
	
	@NotBlank(message = "메세지를 입력해주세요.")
	private String cmMessage;
	
	private long cmChatRoomNo;
	
	private LocalDateTime cmDate = LocalDateTime.now();

}
