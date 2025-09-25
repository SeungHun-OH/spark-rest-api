package com.spark.dating.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

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
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.error.ChatErrorCode;
import com.spark.dating.dto.chat.ChatMessage;
import com.spark.dating.dto.chat.ChatRoom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/matching-chat")
//@Tag(name = "채팅 컨트롤러", description = "채팅 관련해서 처리하는 컨트롤러입니다.")
@RestController
public class ChatController implements ChatControllerDocs{

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/rooms")
	public String createChatRoom(@RequestParam("matchingNo") final int matchingNo) {
		chatRoomService.createChatRoom(matchingNo);
		return "SUCCESS";
	}

	@GetMapping("/rooms")
	public List<ChatRoom> chatRoomList(@RequestParam("m_id") final int userNo) {
		return chatRoomService.selectAllChatRoom(userNo);
	}

	@GetMapping("/chatting/{roomId}")
	public List<ChatMessage> chatMessage(@PathVariable("roomId") final int roomId) {
		if(roomId>0) {
//			throw new RestApiException(ChatErrorCode.INVALID_PARAMETER);
			throw new NullPointerException();
		}
		return chatMessageService.getChattingMessage(roomId);
	}

	@Delete("/leave/{roomId}")	// 보류
	public Map<String, Object> leaveChatRoom(@PathVariable("roomId") final int roomId) {
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