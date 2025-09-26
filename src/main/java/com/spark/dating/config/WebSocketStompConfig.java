package com.spark.dating.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat").setAllowedOrigins("*");
	}

	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메시지를 구독하는 클라이언트들에게 메세지 전달
		registry.enableSimpleBroker("/sub");
		// 클라이언트에서 보낸 메세지를 받을 prefix
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
}
