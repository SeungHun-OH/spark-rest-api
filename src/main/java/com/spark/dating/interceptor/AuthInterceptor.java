package com.spark.dating.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.dto.member.Member;
import com.spark.dating.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true; // 컨트롤러 메서드가 아닌 경우는 그냥 통과
		}

		String jwtToken = jwtUtil.getToken(request.getHeader("Authorization"));
//		String token = jwtUtil.generateToken(2L);
		System.err.println(jwtToken);
		if (jwtToken != null && !jwtToken.equals("")) {
			if (jwtUtil.isValidToken(jwtToken)) {
				if (jwtUtil.getMemberNo(jwtToken) == null) {
					throw new RestApiException(JwtErrorCode.EMPTY_JWT);
				}
				Member member = new Member();
				member.setMNo(jwtUtil.getMemberNo(jwtToken).intValue());
				System.err.println(member.toString());
				System.err.println(jwtUtil.parseClaims(jwtToken));
				if (!jwtUtil.existsByNo((jwtUtil.getMemberNo(jwtToken)))) {
					throw new RestApiException(JwtErrorCode.USER_NOT_FOUND);
				}
				
				AuthenticationContextHolder.setContext(member);

				return true;
			}
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		AuthenticationContextHolder.clear();
	}
}
