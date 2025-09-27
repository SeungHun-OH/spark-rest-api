package com.spark.dating.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.spark.dating.interceptor.ChatChannelInterceptor;
import com.spark.dating.interceptor.ChatHandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer{

	
	@Bean
	public ChatHandshakeInterceptor chatHandshakeInterceptor() {
		return new ChatHandshakeInterceptor();
	}
	
	@Bean
	public ChatChannelInterceptor chatChannelInterceptor() {
		return new ChatChannelInterceptor();
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/ws-stomp").setAllowedOrigins("*").addInterceptors(chatHandshakeInterceptor());
	}
	
	@Override
	public void configureClientInboundChannel(ChannelRegistration registration) {
		registration.interceptors(chatChannelInterceptor());
	}
	
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		// 메시지를 구독하는 클라이언트들에게 메세지 전달
		registry.enableSimpleBroker("/sub");
		// 클라이언트에서 보낸 메세지를 받을 prefix
		registry.setApplicationDestinationPrefixes("/pub");
	}
	
//	@EventListener
//	public void onDisconnectEvent(SessionDisconnectEvent event) {
//		String session = event.getSessionId();
//		System.err.println(session+" "+ event.getUser());
//		
//	}
	
}
