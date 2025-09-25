package com.spark.dating.common.error;

import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

	String getCode();
	HttpStatus getHttpStatus();
	String getMessage();
}
