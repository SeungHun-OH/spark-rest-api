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
	INVALID_PARAMETER("CH002", HttpStatus.BAD_REQUEST, "파라미터(인자)가 잘못됨"),
	;
	

	private final String code;
	@JsonIgnore
	private final HttpStatus httpStatus;
    private final String message;
}
