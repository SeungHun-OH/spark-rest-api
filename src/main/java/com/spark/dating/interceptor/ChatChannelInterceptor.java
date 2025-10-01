package com.spark.dating.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.ChatErrorCode;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.dto.member.Member;
import com.spark.dating.utils.JwtUtil;
import com.spark.dating.utils.UuidBase62Utils;

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

		switch (accessor.getCommand()) {
		case CONNECT:
			String jwtToken = jwtUtil.getToken(accessor.getFirstNativeHeader("Authorization"));
			log.info(jwtToken);
//			String token = jwtUtil.generateToken(2L);
			if (jwtUtil.isValidToken(jwtToken)) {
				if (jwtUtil.getMemberNo(jwtToken) == null) {
					throw new RestApiException(JwtErrorCode.EMPTY_JWT);
				}
				Member member = new Member();
				member.setMNo(jwtUtil.getMemberNo(jwtToken).intValue());
				log.info(member.toString());
				log.info(jwtUtil.parseClaims(jwtToken)+"");
				if (!jwtUtil.existsByNo((jwtUtil.getMemberNo(jwtToken)))) {
					throw new RestApiException(JwtErrorCode.USER_NOT_FOUND);
				}
				accessor.getSessionAttributes().put("memberNo", jwtUtil.getMemberNo(jwtToken));
//				accessor.setUser(new StompPrincipal(jwtUtil.getMemberNo(jwtToken).toString()));
				Set<String> chatroomBase62UuidList = chatRoomService
						.selectAllChatRoomUUID(accessor.getSessionAttributes().get("memberNo").toString());
				if (!chatroomBase62UuidList.isEmpty() || chatroomBase62UuidList != null) {
					accessor.getSessionAttributes().put("rooms", chatroomBase62UuidList);
				}

			}
			break;
		case SEND:
			
			break;
		case SUBSCRIBE:
			Map<String, Object> sessionAttribute = accessor.getSessionAttributes();
			if (sessionAttribute != null && sessionAttribute.containsKey("rooms")) {
				Set<String> rooms = (Set<String>) accessor.getSessionAttributes().get("rooms");
				String userDestinationRoomUUID = accessor.getDestination().substring(10);
				log.info(rooms.toString());
				log.info("des:  " + userDestinationRoomUUID);
				if (rooms.contains(userDestinationRoomUUID)) {
					log.info("presend 구독");
					log.info("올바른 채팅");
				} else {
					Map<String, Object> memberNoAndUuid = new HashMap<>();
					memberNoAndUuid.put("memberNo", accessor.getSessionAttributes().get("memberNo").toString());
					memberNoAndUuid.put("roomUUID",
							UuidBase62Utils.fromBase62(userDestinationRoomUUID).toString().replace("-", ""));
					if (chatRoomService.existsChatroomByMemberNoAndUuid(memberNoAndUuid) == false) {
						throw new RestApiException(ChatErrorCode.USER_NOT_IN_CHAT);
					}
					rooms.add(userDestinationRoomUUID);
					sessionAttribute.put("rooms", rooms);
					log.info("해당 방 추가" + sessionAttribute.get("rooms"));
				}
			}
			break;
		case UNSUBSCRIBE:
			// 추후에 UNSUBSCRIBE처리 시 새로운 채팅방 select 후 session 최신화 할것
			break;
		case DISCONNECT:
			break;
		default:
			break;
		}

		return message;
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel, boolean sent) {
		accessor = StompHeaderAccessor.wrap(message);
//		switch (accessor.getCommand()) {
//		case CONNECT:
//			// 유저가 Websocket으로 connect()를 한 뒤 호출됨
//			System.err.println("postsend 연결" + accessor.toString());
//			break;
//
//		case SUBSCRIBE:
//			System.err.println("postsend 구독");
//
//			break;
//
//		case DISCONNECT:
//			log.info("DISCONNECT 되었습니다.");
//			System.err.println(accessor.toString());
//			log.info("sessionId: {}", sessionId);
//			log.info("channel:{}", channel);
//			break;
//
//		default:
//			break;
//		}

	}
}
