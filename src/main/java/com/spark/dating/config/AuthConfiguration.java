package com.spark.dating.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.spark.dating.interceptor.AuthInterceptor;

@Configuration
public class AuthConfiguration implements WebMvcConfigurer{

	@Autowired
	private AuthInterceptor authInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(authInterceptor)

	  //
		.excludePathPatterns("/images/**", "/feedPicture/picture/*", "/favicon/ico", "/member/create", "/member/login", 
		"/member/picture" ,"/thread/boardList", "/thread/board"
		);

		// .excludePathPatterns("/images/**", "/feedPicture/picture/*", "/favicon/ico", "/member/**");
	}
}
