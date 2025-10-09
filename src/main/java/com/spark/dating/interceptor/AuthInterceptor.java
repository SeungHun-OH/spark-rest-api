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
import com.spark.dating.member.MemberService;
import com.spark.dating.utils.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private MemberService	memberService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		log.info(handler.getClass() + "");
		if (!(handler instanceof HandlerMethod)) {
			return true; // 컨트롤러 메서드가 아닌 경우는 그냥 통과
		}

		// -------------------------------------------------
	  // 로그인시 JWT 검증은 예외가 필요해서 추후에 적용할것
		// String jwtToken = jwtUtil.getToken(request.getHeader("Authorization"));

    // String token = jwtUtil.generateToken(2L);

		// log.info("JWT 토큰 {}", jwtToken);
		// if (jwtUtil.isValidToken(jwtToken)) {
		// 	Long memberNo = jwtUtil.getMemberNo(jwtToken);
		// 	if (memberNo == null) {
		// 		throw new RestApiException(JwtErrorCode.EMPTY_JWT);
		// 	}
		// 	Member member = new Member();
		// 	member.setMNo(memberNo.intValue());
		// 	log.info("JWT 토큰 파싱 {}", jwtUtil.parseClaims(jwtToken));
		// 	if (!jwtUtil.existsByNo(memberNo)) {
		// 		throw new RestApiException(JwtErrorCode.USER_NOT_FOUND);
		// 	}

		// 	AuthenticationContextHolder.setContext(member);

		// 	return true;
		// }
		// return false;
		//  


		// -------------------------------------------------
	  // JwtUtil.existsByNo 구조 -> memberService.existsByNo 구조로 변경
		String jwtToken = jwtUtil.getToken(request.getHeader("Authorization"));

    String token = jwtUtil.generateToken(2L);

		log.info("JWT 토큰 {}", jwtToken);
		if (jwtUtil.isValidToken(jwtToken)) {
			Long memberNo = jwtUtil.getMemberNo(jwtToken);
			if (memberNo == null) {
				throw new RestApiException(JwtErrorCode.EMPTY_JWT);
			}
			Member member = new Member();
			member.setMNo(memberNo.intValue());
			log.info("JWT 토큰 파싱 {}", jwtUtil.parseClaims(jwtToken));
			if (!memberService.existsByNo(memberNo)) {
				throw new RestApiException(JwtErrorCode.USER_NOT_FOUND);
			}
			AuthenticationContextHolder.setContext(member);

			return true;
		}
		return false;
		 
		// return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		AuthenticationContextHolder.clear();
	}
}
