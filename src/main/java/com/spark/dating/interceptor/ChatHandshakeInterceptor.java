package com.spark.dating.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.spark.dating.chat.service.StompService;
import com.spark.dating.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

	@Autowired
	private StompService stompService;
	@Autowired
	private JwtUtil jwtUtil;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
		Map<String, Object> attributes) throws Exception {
		// 추후 jwt를 통해 로그인 검증 과정 추가
		String jwt = "";
		String memberId = "test";

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request; // 추후에 헤더에서 jwt 가져오는데 사용
			//attributes.put("memberId", memberId);
		}
    // 추후 jwt유효 검증 및 아이디 검증 
    // if (stompService.isMemberExist(memberId) == 0) {
    // return false;}
		log.info("핸드셰이크 ~~~~~~~~~~");
		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {
	}
	
	@EventListener
	public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
	    StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
	    String sessionId = accessor.getSessionId();
	    Long memberId = (Long) accessor.getSessionAttributes().get("memberId");

			
	    // 세션 데이터 정리
	    log.info("세션 종료: {} (사용자: {})", sessionId, memberId);
	}
}
