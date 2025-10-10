package com.spark.dating.interceptor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.StompPrincipal;
import com.spark.dating.common.exception.JwtErrorCode;
import com.spark.dating.dto.member.Member;
import com.spark.dating.member.MemberService;
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

	//JwtUtil -> MemberService에서 JwtUtil을 주입받기 때문에 순환참조 문제가 발생할 수 있음
	@Autowired
	private MemberService memberService;

	private StompHeaderAccessor accessor;

	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		accessor = StompHeaderAccessor.wrap(message);
		StompCommand command = accessor.getCommand();
		String destination = accessor.getDestination();
//		log.info("Headers: {}", message.getHeaders());
		if (command != null) {
			switch (command) {
			case CONNECT:
				String jwtToken = jwtUtil.getToken(accessor.getFirstNativeHeader("Authorization"));
				log.info(jwtToken);
//			String token = jwtUtil.generateToken(2L);
				if (jwtUtil.isValidToken(jwtToken)) {
					if (jwtUtil.getMemberNo(jwtToken) == null) {
						throw new RestApiException(JwtErrorCode.EMPTY_JWT);
					}
					Member member = new Member();
					Long memberNo = jwtUtil.getMemberNo(jwtToken);
					member.setMNo(memberNo.intValue());
					log.info(member.toString());
					log.info(jwtUtil.parseClaims(jwtToken) + "");
					if (!memberService.existsByNo(memberNo)) {
						throw new RestApiException(JwtErrorCode.USER_NOT_FOUND);
					}
					accessor.getSessionAttributes().put("memberNo", memberNo);
					accessor.setUser(new StompPrincipal(memberNo.toString()));
					Set<String> chatroomBase62UuidList = chatRoomService.selectAllChatRoomUUID(memberNo.toString());

					if (chatroomBase62UuidList != null && !chatroomBase62UuidList.isEmpty()) {
						accessor.getSessionAttributes().put("rooms", chatroomBase62UuidList);
					}
				}
				break;
			case SEND:
				break;
			case SUBSCRIBE:
				if (!(destination.equals("/sub/room") || destination.startsWith("/sub/status") || destination.startsWith("/user/sub/errors"))) {
					Map<String, Object> sessionAttribute = accessor.getSessionAttributes();
					if (sessionAttribute != null && sessionAttribute.containsKey("rooms")) {
						Set<String> rooms = (Set<String>) accessor.getSessionAttributes().get("rooms");
						System.err.println(accessor.getDestination());
						String userDestinationRoomUUID = accessor.getDestination().substring(10);
						log.info(rooms.toString());
						log.info("des:  " + userDestinationRoomUUID);
						if (rooms.contains(userDestinationRoomUUID)) {
							log.info("presend 구독");
							log.info(destination+" 채팅방");
						} else {
							Map<String, Object> memberNoAndUuid = new HashMap<>();
							memberNoAndUuid.put("memberNo", accessor.getSessionAttributes().get("memberNo").toString());
							memberNoAndUuid.put("roomUUID",
									UuidBase62Utils.fromBase62(userDestinationRoomUUID).toString().replace("-", ""));
							if (chatRoomService.existsChatroomByMemberNoAndUuid(memberNoAndUuid) == false) {
//								log.warn(accessor.getUser().getName()+"                  -----");
//								messagingTemplate.convertAndSendToUser(accessor.getUser().getName(), "/sub/errors", "권한이 없는 채팅방입니다.");
								return null; // 원래 SUBSCRIBE 프레임은 무시
							}

							rooms.add(userDestinationRoomUUID);
							sessionAttribute.put("rooms", rooms);
							log.info("해당 방 추가" + sessionAttribute.get("rooms"));
						}
					}
				}
				break;
			case UNSUBSCRIBE:
				destination = accessor.getDestination();
				break;
			case DISCONNECT:
				break;
			default:
				break;
			}
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
