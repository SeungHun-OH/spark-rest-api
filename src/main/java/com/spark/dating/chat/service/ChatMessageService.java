package com.spark.dating.chat.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.dating.chat.dao.ChatMessageDao;
import com.spark.dating.dto.chat.ChatMemberProfile;
import com.spark.dating.dto.chat.ChatMessage;
import com.spark.dating.dto.chat.ChatMessageSelect;
import com.spark.dating.dto.chat.ChatMessageSendRequest;
import com.spark.dating.dto.chat.ChatMessageSendResponse;
import com.spark.dating.dto.chat.ChatMessageWithMemberResponse;
import com.spark.dating.dto.chat.ChatRoomEvent;
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
	
	@Transactional
	public void insertChatMessage(Long sendMemberNo,String chatBase62RoomUUID, ChatMessageSendRequest request) {
		
		String chatRoomUUID = UuidBase62Utils.fromBase62(chatBase62RoomUUID).toString().replace("-", "").toUpperCase();
		
		Long chatRoomNo = chatRoomService.findChatRoomIdByUuid(chatRoomUUID);
		
		String message = request.getCmMessage();
		ChatMessage chatMessage = ChatMessage.builder().cmMessage(message).cmSendMember(sendMemberNo).cmChatRoomNo(chatRoomNo).build();
		log.info("chatMessageSend {}"+chatMessage.toString());
		chatMessageDao.insertChatMessage(chatMessage);
		log.info("chatMessageSend==========================");
		chatMessageDao.updateLastMessage(chatMessage);
		log.info("chatMessageSend++++++++++++++++++++++++++");
		simpMessagingTemplate.convertAndSend("/sub/room", ChatRoomEvent.builder().lastMessage(message).chatRoomBase62RoomUUID(chatBase62RoomUUID).lastMessageDate(chatMessage.getCmDate()).build());
		simpMessagingTemplate.convertAndSend("/sub/room/"+chatBase62RoomUUID, ChatMessageSendResponse.from(chatMessage, request));
	}
	
	@Transactional
	public ChatMessageWithMemberResponse getChattingMessage(String chatBase62RoomUUID, int memberNo){
		String chatRoomUUID = UuidBase62Utils.fromBase62(chatBase62RoomUUID).toString().replace("-", "").toUpperCase();
		Long chatRoomNo = chatRoomService.findChatRoomIdByUuid(chatRoomUUID);
		Map<String, Object> chatMessageMap = new HashMap<>();
		chatMessageMap.put("chatRoomNo", chatRoomNo);
		chatMessageMap.put("memberNo", memberNo);
		chatMessageDao.updateReadDate(chatMessageMap);
		List<ChatMessageSelect> messages = chatMessageDao.getChattingMessage(chatMessageMap);
		Long opponentMno = chatMessageDao.getOpponentMnoByChatRoom(chatMessageMap);
		ChatMemberProfile profile = chatMessageDao.getOpponentProfileByMno(opponentMno);
		ChatMessageWithMemberResponse response = ChatMessageWithMemberResponse.builder().chatMemberProfile(profile).chatMessages(messages).build();
		return response;
	}
}
