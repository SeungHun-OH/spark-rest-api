package com.spark.dating.common;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import com.spark.dating.common.error.BaseErrorCode;
import com.spark.dating.common.error.CommonErrorCode;


@RestControllerAdvice(basePackages = "com.spark")
public class ResponseWrapper implements ResponseBodyAdvice<Object>{

	@Override
	public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
		return true;
//		return returnType.getDeclaringClass().getPackageName().startsWith("com.spark.dating.chat.controller");
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
			Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request,
			ServerHttpResponse response) {
		String path = request.getURI().getPath();
		

		if (body instanceof BaseErrorCode baseErrorCode) {
            System.out.println(baseErrorCode.getCode()+" "+baseErrorCode.getMessage());
            System.out.println(baseErrorCode.toString());
            response.setStatusCode(baseErrorCode.getHttpStatus());
            return RestApiResponse.builder()
            	.status("Fail")
                .path(path)
                .error(baseErrorCode)
                .build();
        }
				
		return RestApiResponse.builder().status("Success").path(path).data(body).build();
	}
	
		@ExceptionHandler(RestApiException.class)
		public ResponseEntity<BaseErrorCode> handleException(RestApiException e) {
			System.out.println(e.getErrorCode().getHttpStatus());
			return ResponseEntity.status(e.getErrorCode().getHttpStatus()).body(e.getErrorCode());
	
		}
		
		@ExceptionHandler(Exception.class)
	    public ResponseEntity<BaseErrorCode> handleUnexpected(Exception e) {
	        return ResponseEntity
	                .status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
	                .body(CommonErrorCode.INTERNAL_SERVER_ERROR);
	    }
		
		
}
