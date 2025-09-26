package com.spark.dating.common.exception;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@JsonFormat(shape = Shape.OBJECT)
public enum CommonErrorCode implements BaseErrorCode {

	INVALID_PARAMETER(HttpStatus.BAD_REQUEST.value() + "", HttpStatus.BAD_REQUEST, "잘못된 요청"),
	RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND.value() + "", HttpStatus.NOT_FOUND, "요청한 리소스를 찾을 수 없음"),
	INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value() + "", HttpStatus.INTERNAL_SERVER_ERROR,
			"요청을 처리할 수 없음"),
	;

	private final String code;
	@JsonIgnore
	private final HttpStatus httpStatus;
	private final String message;
}
