package com.spark.dating.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.LoginErrorCode;
import com.spark.dating.dto.login.AuthMember;
import com.spark.dating.dto.login.LoginRequest;
import com.spark.dating.dto.login.LoginResponse;
import com.spark.dating.dto.login.MyInfo;
import com.spark.dating.login.dao.LoginDao;
import com.spark.dating.utils.JwtUtil;

@Service
public class LoginService {

	@Autowired
	private LoginDao loginDao;
	
//	@Autowired
//	private JwtUtil jwtUtil;

//	public LoginResponse loginAndGenerateToken(LoginRequest request) {
//		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//		AuthMember authMember = loginDao.selectMemberById(request.getMemberId());
//		if (authMember == null) {
//			throw new RestApiException(LoginErrorCode.AUTH_INVALID_CREDENTIALS);
//		}
//
//		if (!passwordEncoder.matches(request.getMemberPwd(), authMember.getMPassword())) {
//			throw new RestApiException(LoginErrorCode.AUTH_INVALID_CREDENTIALS);
//		}
//		
//		System.out.println(authMember.toString());
//		String jwtToken = jwtUtil.generateToken(authMember);
//		System.err.println("성공");
//		return LoginResponse.builder().JwtToken(jwtToken).memberUuid(authMember.getMUuid()).build();
//	}
	
	public MyInfo myInfo(int memberNo) {
		return loginDao.selectMemberByMno(memberNo);
	}

}
