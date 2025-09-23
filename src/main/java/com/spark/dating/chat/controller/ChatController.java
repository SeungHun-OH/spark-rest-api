package com.spark.dating.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatMessageService;
import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.dto.chat.ChatMessage;
import com.spark.dating.dto.chat.ChatRoom;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/matching-chat")
@RestController
public class ChatController {

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/rooms")
	@Operation(summary = "채팅방 생성", description = "매칭을 수락하면 machingNo를 받아 채팅방을 만듭니다.")
	public String createChatRoom(@RequestParam("matchingNo") int matchingNo) {
		chatRoomService.createChatRoom(matchingNo);
		return "SUCCESS";
	}

	@GetMapping("/rooms")
	@Operation(summary = "채팅방 조회", description = "로그인한 사용자의 m_id를 통해 채팅방 리스트를 조회합니다.")
	public Map<String, Object> chatRoomList(@RequestParam("m_id") int userNo) {

		Map<String, Object> result = new HashMap<>();
		List<ChatRoom> chatRooms = chatRoomService.selectAllChatRoom(userNo);
		result.put("Success", chatRooms);

		return result;
	}

	@GetMapping("/chatting/{roomId}")
	@Operation(summary = "채팅방 메세지 조회", description = "클라이언트가 요청한 rooId를 통해 해당 방에 있는 메세지들을 조회합니다.")
	public Map<String, Object> chatMessage(@PathVariable("roomId") Integer roomId) {
		Map<String, Object> result = new HashMap<>();
		System.out.println("=========" + roomId);
		List<ChatMessage> chatMessages = chatMessageService.getChattingMessage(roomId);
		System.out.println(chatMessages.toString());

		result.put("성공", chatMessages);
		return result;
	}
	
	@Delete("/leave/{roomId}")
	public Map<String, Object> leaveChatRoom(@PathVariable("roomId") int roomId){
		Map<String, Object> resultMap = new HashMap<>();
		
		return resultMap;
	}
	

	@MessageMapping("/room/{roomId}")
//	@SendTo("sub/room/{roomId}")
	public void sendMessage(@DestinationVariable("roomId") int roomId, ChatMessage sendChatData) {

		ChatMessage chatMessage = ChatMessage.createChatMessage(sendChatData.getCm_senduser(), roomId,
				sendChatData.getCm_msg());

		System.out.println("연결");

		log.info(chatMessage.toString() + "-----------");
		chatMessageService.insertChatMessage(chatMessage);
		chatMessageService.sendMessage(roomId, chatMessage.getCm_msg());
	}
}