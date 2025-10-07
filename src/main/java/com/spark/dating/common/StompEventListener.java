package com.spark.dating.common;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.spark.dating.dto.chat.MemberStatus;
import com.spark.dating.dto.chat.MemberStatusMessage;
import com.spark.dating.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StompEventListener {

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private SessionRegistry sessionRegistry;
	
	@EventListener
	public void handleSessionConnected(SessionConnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
		Long memberNo = (Long) accessor.getSessionAttributes().get("memberNo");
		if(memberNo == null) {
			System.err.println("connect principle LONG 값 "+memberNo);
			System.err.println("connect principle LONG 값 "+accessor.toString());
			return ;
		}
//		String nickName = memberService.selectMnickNameByMno(memberNo);
		String memberUuid = memberService.getMemberUuidByMemberNo(memberNo);
		MemberStatusMessage statusMessage = MemberStatusMessage.builder().memberNO(memberNo).memberUuid(memberUuid)
				.memberStatus(MemberStatus.ONLINE).build();
//		if (memberNo != null && !sessionRegistry.isSessionActive(memberNo)) {
		System.err.println("이벤트 리스터 memberNo null을 위한 에러 확인용    "+memberNo);
//		if (!sessionRegistry.isSessionActive(memberNo)) {
//			String uuid = UUID.randomUUID().toString();
//			sessionRegistry.addMapping(memberNo, uuid);
//			statusMessage.setMemberUuid(uuid);
//		}

		log.info("connect status msg 11 {}", statusMessage.toString());
		memberService.updateMemberStatusInfo(statusMessage);
//		statusMessage.setMemberUuid(sessionRegistry.getUuid(memberNo));
		log.info("connect status msg 22 {}", statusMessage.toString());
		System.out.println("statujs  "+ statusMessage.toString());
		messagingTemplate.convertAndSend("/sub/status", statusMessage);
	}

	@EventListener
	public void handleSessionDisconnect(SessionDisconnectEvent event) {
		StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

		String sessionId = accessor.getSessionId();

		Long memberNo = (Long) accessor.getSessionAttributes().get("memberNo");
		
		if(memberNo == null) {
			System.err.println("disconnect principle LONG 값 "+memberNo);
			return ;
		}
		
		
		String memberUuid = memberService.getMemberUuidByMemberNo(memberNo);
		
		MemberStatusMessage statusMessage = MemberStatusMessage.builder().memberUuid(sessionRegistry.getUuid(memberNo)).memberNO(memberNo)
				.memberUuid(memberUuid).memberStatus(MemberStatus.OFFLINE).build();
		System.err.println("mem status   "+statusMessage.toString());
		memberService.updateMemberStatusInfo(statusMessage);

		messagingTemplate.convertAndSend("/sub/status", statusMessage);
		Map<String, Object> sessionAttributes = accessor.getSessionAttributes();
//		if (sessionRegistry.isSessionActive(memberNo)) {
//			sessionRegistry.removeByMno(memberNo);
//		}

		if (sessionAttributes != null) {
			sessionAttributes.clear();
		}
		log.info("세션 종료 및 attribute 삭제 완료: " + sessionId);

	}
}