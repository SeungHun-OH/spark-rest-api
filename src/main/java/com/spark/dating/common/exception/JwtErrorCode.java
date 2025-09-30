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
public enum JwtErrorCode implements BaseErrorCode {

    INVALID_JWT("J001", HttpStatus.FORBIDDEN, "유효하지 않은 JWT 토큰"),
    EXPIRED_JWT("J002", HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰"),
    UNSUPPORTED_JWT("J003", HttpStatus.BAD_REQUEST, "지원하지 않는 JWT 토큰"),
    EMPTY_JWT("J004", HttpStatus.BAD_REQUEST, "JWT 클레임이 비어있음"),
    USER_NOT_FOUND("J005", HttpStatus.UNAUTHORIZED, "해당 토큰의 사용자가 존재하지 않음"),
    ;
	

	private final String code;
	@JsonIgnore
	private final HttpStatus httpStatus;
    private final String message;
}
