package com.spark.dating.common;

import com.spark.dating.common.exception.BaseErrorCode;

import lombok.Getter;

@Getter
public class RestApiException extends RuntimeException{

	private final BaseErrorCode errorCode;

	public RestApiException(BaseErrorCode errorCode) {
		super(errorCode.getMessage());
		this.errorCode = errorCode;
	}
	
}