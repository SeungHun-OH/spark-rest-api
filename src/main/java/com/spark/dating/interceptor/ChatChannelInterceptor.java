package com.spark.dating.interceptor;

import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.StompPrincipal;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.dto.member.Member;
import com.spark.dating.utils.JwtUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ChatChannelInterceptor implements ChannelInterceptor {

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private ChatRoomService chatRoomService;

	private StompHeaderAccessor accessor;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		accessor = StompHeaderAccessor.wrap(message);
		if (accessor.getCommand() == StompCommand.CONNECT) {
			// 최초 연결시 토큰 검증하면 될 듯(아래 주소 참고)
			// https://blog.naver.com/cutesboy3/221479750532

			String jwtToken = jwtUtil.getToken(accessor.getFirstNativeHeader("Authorization"));
			System.err.println(jwtToken);
//			String token = jwtUtil.generateToken(2L);
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
				accessor.setUser(new StompPrincipal(jwtUtil.getMemberNo(jwtToken).toString()));
				Set<String> chatroomBase62UuidList = chatRoomService.selectAllChatRoomUUID(accessor.getUser().getName());
				if(!chatroomBase62UuidList.isEmpty() || chatroomBase62UuidList != null) {
					System.err.println(chatroomBase62UuidList.toString());
					accessor.getSessionAttributes().put("rooms", chatroomBase62UuidList);
				}
//				for (UUID u : chatroomUuidList) {
//					System.out.println(u.toString());
//				}
			}
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
			System.err.println("postsend 연결" + accessor.toString());
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
