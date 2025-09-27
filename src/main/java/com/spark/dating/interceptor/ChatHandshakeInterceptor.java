package com.spark.dating.interceptor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import com.spark.dating.chat.service.StompService;

@Component
public class ChatHandshakeInterceptor implements HandshakeInterceptor {

	@Autowired
	private StompService stompService;

	@Override
	public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Map<String, Object> attributes) throws Exception {
		// 추후 jwt를 통해 로그인 검증 과정 추가
		String jwt = "";
		String memberId = "test";

		if (stompService.isMemberExist(memberId) == 0) {
			return false;
		}

		if (request instanceof ServletServerHttpRequest) {
			ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request; // 추후에 헤더에서 jwt 가져오는데 사용
			attributes.put("memberId", memberId); // 추후에 jwt저장하면 될듯
		}

		return true;
	}

	@Override
	public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
			Exception exception) {

	}

}
