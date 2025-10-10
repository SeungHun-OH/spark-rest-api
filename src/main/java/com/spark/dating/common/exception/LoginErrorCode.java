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
public enum LoginErrorCode implements BaseErrorCode {

	AUTH_INVALID_CREDENTIALS("LG001", HttpStatus.UNAUTHORIZED, "아이디 또는 비밀번호가 올바르지 않음"),
	;
	

	private final String code;
	@JsonIgnore
	private final HttpStatus httpStatus;
    private final String message;
}
