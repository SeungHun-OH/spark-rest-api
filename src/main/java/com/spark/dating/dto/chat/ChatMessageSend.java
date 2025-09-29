package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatMessageSend {

	private Long cmNo;
	
//	@NotNull(message = "사용자 번호는 필수입니다.")
//	@Min(value = 1, message = "올바른 사용자 번호가 아닙니다.")
//	@Positive(message = "올바른 사용자 번호가 아닙니다.")
	private Long cmSendUser;
	
	@NotBlank(message = "메세지를 입력해주세요.")
	private String cmMessage;
	
	private Long cmChatRoomNo;
	
	private LocalDateTime cmDate = LocalDateTime.now();

}
