package com.spark.dating.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.spark.dating.chat.dao.ChatMessageDao;
import com.spark.dating.dto.chat.ChatMessageSelectResponse;
import com.spark.dating.dto.chat.ChatMessageSend;
import com.spark.dating.utils.UuidBase62Utils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ChatMessageService {

	@Autowired
	private ChatMessageDao chatMessageDao;
	
	@Autowired
	private ChatRoomService chatRoomService;
	
	@Autowired
	private SimpMessagingTemplate simpMessagingTemplate;


	public void sendMessage(String roomId, String message) {
		simpMessagingTemplate.convertAndSend("/sub/room/"+roomId, message);
	}
	
	public void insertChatMessage(Long sendMemberNo,String chatBase62RoomUUID, ChatMessageSend chatMessageSend) {
		
		String chatRoomUUID = UuidBase62Utils.fromBase62(chatBase62RoomUUID).toString().replace("-", "").toUpperCase();
		
		Long chatRoomNo = chatRoomService.findChatRoomIdByUuid(chatRoomUUID);
		chatMessageSend = chatMessageSend.toBuilder().cmSendMember(sendMemberNo).cmChatRoomNo(chatRoomNo).build();
		log.info(chatMessageSend.toString());
		chatMessageDao.insertChatMessage(chatMessageSend);
		simpMessagingTemplate.convertAndSend("/sub/room/"+chatBase62RoomUUID, chatMessageSend.getCmMessage());
	}
	
	public List<ChatMessageSelectResponse> getChattingMessage(String chatBase62RoomUUID, int memberNo){
		String chatRoomUUID = UuidBase62Utils.fromBase62(chatBase62RoomUUID).toString().replace("-", "").toUpperCase();
		Long chatRoomNo = chatRoomService.findChatRoomIdByUuid(chatRoomUUID);
		Map<String, Object> chatMessageMap = new HashMap<>();
		chatMessageMap.put("chatRoomNo", chatRoomNo);
		chatMessageMap.put("memberNo", memberNo);		
		return chatMessageDao.getChattingMessage(chatMessageMap);
	}
}
