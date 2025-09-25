package com.spark.dating.common;

import java.time.LocalDateTime;

import com.spark.dating.common.exception.BaseErrorCode;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RestApiResponse<T> {

	
	private final LocalDateTime timestamp = LocalDateTime.now();
	private String status;
    private T data;
    private String path;
    private BaseErrorCode error;

	@Builder
    private RestApiResponse(String status, T data, String path, BaseErrorCode error) {
        this.status = status;
        this.data = data;
        this.path = path;
        this.error = error;
    }
}