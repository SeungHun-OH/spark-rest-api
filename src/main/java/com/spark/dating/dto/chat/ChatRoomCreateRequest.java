package com.spark.dating.dto.chat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ChatRoomCreateRequest {
	
	@NotNull(message = "매칭번호는 필수입니다.")
	@Min(value = 1, message = "올바른 매칭번호가 아닙니다.")
	@Positive(message = "매칭번호는 양수여야 합니다.")
	private Long matchingNo;
	
}
