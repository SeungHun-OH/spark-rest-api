package com.spark.dating.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor{


	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
		    return true; // 컨트롤러 메서드가 아닌 경우는 그냥 통과
		}

//		String jwt = jwtUtil.generateToken("test1");
		String jwtToken = jwtUtil.getToken(request.getHeader("Authorization"));
//		System.err.println(jwt);
		if(jwtToken != null && !jwtToken.equals("")) {
			log.info("여기ㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣㅣ");
			if(jwtUtil.isValidToken(jwtToken)) {
				//localtread작업 ㄱ
				if(jwtUtil.isTokenExpired(jwtToken)) {
					throw new RestApiException(JwtErrorCode.EXPIRED_JWT); 
				}
				log.info(jwtUtil.parseClaims(jwtToken)+"");
				return true;
			}
		}

		
		return false;
	}
}
