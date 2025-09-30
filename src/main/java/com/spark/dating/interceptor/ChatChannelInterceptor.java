package com.spark.dating.interceptor;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatChannelInterceptor implements ChannelInterceptor {

	private StompHeaderAccessor accessor;
	
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		accessor = StompHeaderAccessor.wrap(message);
		if (accessor.getCommand() == StompCommand.CONNECT) {
			// 최초 연결시 토큰 검증하면 될 듯(아래 주소 참고)
			// https://blog.naver.com/cutesboy3/221479750532
			System.err.println("presend 연결");
		}

		if (accessor.getCommand() == StompCommand.SUBSCRIBE) {
			// 추후 해당 채팅방에 들어올 수 있는 유저인지 검증(유저의 채팅방 리스트)
			System.err.println("presend 구독");
		}
		
		if (accessor.getCommand() == StompCommand.UNSUBSCRIBE) {
			
		}

		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		accessor = StompHeaderAccessor.wrap(message);
		String sessionId = accessor.getSessionId();
		switch (accessor.getCommand()) {
		case CONNECT:
			// 유저가 Websocket으로 connect()를 한 뒤 호출됨
			System.err.println("postsend 연결"+ accessor.toString());
			break;

		case SUBSCRIBE:
			System.err.println("postsend 구독");

			break;

		case DISCONNECT:
			log.info("DISCONNECT 되었습니다.");
			System.err.println(accessor.toString());
			log.info("sessionId: {}", sessionId);
			log.info("channel:{}", channel);
			break;

		default:
			break;
		}

	}
}
