package com.spark.dating.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.login.LoginRequest;
import com.spark.dating.dto.login.LoginResponse;
import com.spark.dating.dto.login.MyInfo;
import com.spark.dating.login.service.LoginService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	
	@GetMapping("/api/myInfo")
	public MyInfo myInfo() {
		final int memberNo = AuthenticationContextHolder.getContextMemberNo();
		return loginService.myInfo(memberNo);
	}
	
//	@PostMapping("/login")
//	public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
//		System.out.println(loginRequest.toString());
//		return loginService.loginAndGenerateToken(loginRequest);
//	}
}
