package com.spark.dating.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.spark.dating.chat.dao.ChatMessageDao;
import com.spark.dating.dto.chat.ChatMessageSelectRequest;
import com.spark.dating.dto.chat.ChatMessageSend;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

	@Autowired
	private ChatMessageDao chatMessageDao;
	private final SimpMessagingTemplate simpMessagingTemplate;


	public void sendMessage(int roomId, String message) {
		simpMessagingTemplate.convertAndSend("/sub/room/"+roomId, message);
	}
	
	public void insertChatMessage(ChatMessageSend chatMessageSend) {
		chatMessageDao.insertChatMessage(chatMessageSend);
	}
	
	public List<ChatMessageSelectRequest> getChattingMessage(int chatroomId){
		return chatMessageDao.getChattingMessage(chatroomId);
	}
}
