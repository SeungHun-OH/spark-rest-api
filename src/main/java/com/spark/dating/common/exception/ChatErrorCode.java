package com.spark.dating.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum ChatErrorCode implements BaseErrorCode {

	USER_NOT_IN_CHAT("CH001", HttpStatus.FORBIDDEN, "채팅방에 참여하지 않은 사용자"),
	USER_NOT_IN_MATCHING("CH002", HttpStatus.FORBIDDEN, "해당 매칭에 참여한 사용자가 아님"),
	
	MATCHING_NOT_FOUND("MT001", HttpStatus.BAD_REQUEST, "해당 매칭 번호가 존재하지 않습니다."),
	;
	

	private final String code;
	@JsonIgnore
	private final HttpStatus httpStatus;
    private final String message;
}
