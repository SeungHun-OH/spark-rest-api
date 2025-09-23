package com.spark.dating.common.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommonErrorCode implements ErrorCode {

	private final HttpStatus httpStatus;
	private final String message;
	
	
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

}
