package com.spark.dating.dto.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ChatRoomCreateRequest {
	
	@NotNull(message = "매칭번호는 필수입니다.")
	@Min(value = 1, message = "올바른 매칭번호가 아닙니다.")
	@Positive(message = "매칭번호는 양수여야 합니다.")
	private Long matchingNo;
	
	@NotNull(message = "사용자번호는 필수입니다.")
	@Min(value = 1, message = "올바른 사용자번호가 아닙니다.")
	@Positive(message = "사용자번호는 양수여야 합니다.")
	private Long memberNo;
	
	@JsonIgnore
	private String chatRoomUUID;
	
	@JsonIgnore
	private long chatRoomNo;
}
