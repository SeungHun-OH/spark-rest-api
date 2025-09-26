package com.spark.dating.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatMessageService;
import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.dto.chat.ChatMessageSelectRequest;
import com.spark.dating.dto.chat.ChatMessageSend;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.ChatRoomSelectRequest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/matching-chat")
@RestController
public class ChatController implements ChatControllerDocs{

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/rooms")
	public void createChatRoom(@Valid @RequestBody ChatRoomCreateRequest ChatRoomCreateRequest) {
		
		chatRoomService.createChatRoom(ChatRoomCreateRequest);
	}

	@GetMapping("/rooms")
	public List<ChatRoomSelectRequest> chatRoomList(@RequestParam("m_id") final int userNo) {
		System.out.println("여기");
		return chatRoomService.selectAllChatRoom(userNo);
	}

	@GetMapping("/chatting/{roomId}")
	public List<ChatMessageSelectRequest> chatMessage(@PathVariable("roomId") final int roomId) {
		return chatMessageService.getChattingMessage(roomId);
	}

	@Delete("/leave/{roomId}")	// 보류
	public Map<String, Object> leaveChatRoom(@PathVariable("roomId") final int roomId) {
		Map<String, Object> resultMap = new HashMap<>();
		return resultMap;
	}
	@Validated
	@MessageMapping("/room/{roomId}")
	@SendTo("sub/room/{roomId}")
	public void sendMessage(@DestinationVariable("roomId") int roomId, @Valid ChatMessageSend chatMessageSend) {
		
		
		//		ChatMessage2 chatMessage = ChatMessage2.createChatMessage(sendChatData.getCm_senduser(), roomId,
		//				sendChatData.getCm_msg());
		log.info(chatMessageSend+toString());
		log.info(roomId+"");

		//		log.info(chatMessage.toString() + "-----------");
		//		chatMessageService.insertChatMessage(chatMessage);
		//		chatMessageService.sendMessage(roomId, chatMessage.getCm_msg());
	}
}