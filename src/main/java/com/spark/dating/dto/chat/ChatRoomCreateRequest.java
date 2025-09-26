package com.spark.dating.dto.chat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

@Getter
public class ChatRoomCreateRequest {
	@NotNull(message = "매칭번호는 필수입니다.")
	@Min(value = 1, message = "올바른 매칭번호가 아닙니다.")
	@Positive(message = "매칭번호는 양수여야 합니다.")
	private Long matchingNo;
	
	@NotNull(message = "유저번호는 필수입니다.")
	@Min(value = 1, message = "올바른 매칭번호가 아닙니다.")
	@Positive(message = "유저번호는 양수여야 합니다.")
	private Long memberNo;

}
