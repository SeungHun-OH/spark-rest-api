package com.spark.dating.chat.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatMessageService;
import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.chat.ChatMessageSelectResponse;
import com.spark.dating.dto.chat.ChatMessageSend;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.ChatRoomSelectRequest;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/matching-chat")
@RestController
public class ChatController implements ChatControllerDocs {

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/rooms")
	public void createChatRoom(@Valid @RequestBody ChatRoomCreateRequest ChatRoomCreateRequest) {
		chatRoomService.createChatRoom(ChatRoomCreateRequest);
	}

	@GetMapping("/rooms")
	public List<ChatRoomSelectRequest> chatRoomList() {
		final int memberNo = AuthenticationContextHolder.getContextMemberNo();
		System.err.println(memberNo);
		return chatRoomService.selectAllChatRoom(memberNo);
	}

	@GetMapping("/chatting/{chatroom-uuid}")
	public List<ChatMessageSelectResponse> chatMessage(@PathVariable("chatroom-uuid") String chatRoomUUID) {
		return chatMessageService.getChattingMessage(chatRoomUUID);
	}

//	@Delete("/leave/{roomId}") //
//	public Map<String, Object> leaveChatRoom(@PathVariable("roomId") final int roomId) {
//		Map<String, Object> resultMap = new HashMap<>();
//		return resultMap;
//	}

	@MessageMapping("/room/{chatroom-uuid}")
//	@SendTo("sub/room/{roomId}")
	public void sendMessage(@DestinationVariable("chatroom-uuid") String chatBase62RoomUUID,
			@Valid ChatMessageSend chatMessageSend,
			@Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {

		log.info(chatBase62RoomUUID);
		Long sendMemberNo = (Long) sessionAttributes.get("memberNo");
		chatMessageService.insertChatMessage(sendMemberNo, chatBase62RoomUUID, chatMessageSend);

	}
}