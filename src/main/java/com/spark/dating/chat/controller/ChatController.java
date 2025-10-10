package com.spark.dating.chat.controller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatMessageService;
import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.chat.ChatMessageSendRequest;
import com.spark.dating.dto.chat.ChatMessageWithMemberResponse;
import com.spark.dating.dto.chat.ChatRoomSelectResponse;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("/chat")
@RestController
public class ChatController implements ChatControllerDocs {

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

//	@PostMapping("/rooms")
//	public void createChatRoom(@Valid @RequestBody ChatRoomCreateRequest ChatRoomCreateRequest) {
//		final int memberNo = AuthenticationContextHolder.getContextMemberNo();
//		chatRoomService.createChatRoom(ChatRoomCreateRequest, memberNo);
//	}

	@GetMapping("/list")
	public List<ChatRoomSelectResponse> chatRoomList() {
		final int memberNo = AuthenticationContextHolder.getContextMemberNo();

		return chatRoomService.selectAllChatRoom(memberNo);
	}

	@GetMapping("/chatting/{chatroom-uuid}")
	public ChatMessageWithMemberResponse chatMessage(@PathVariable("chatroom-uuid") String chatBase62RoomUUID) {
		final int memberNo = AuthenticationContextHolder.getContextMemberNo();

		return chatMessageService.getChattingMessage(chatBase62RoomUUID, memberNo);
	}

	@PostMapping("/message/read")
	public void updateLastMessage(@RequestBody Long lastMessageNo) {
		chatMessageService.updateLastMessage(lastMessageNo);
	}

	@DeleteMapping("{chatroom-uuid}/leave")
	public void leaveChatRoom(@PathVariable("chatroom-uuid") String chatBase62RoomUUID) {
//		Map<String, Object> resultMap = new HashMap<>();
		System.err.println(chatBase62RoomUUID);
		chatRoomService.leaveChatRoom(chatBase62RoomUUID);
		
	}

	@MessageMapping("/room/{chatroom-uuid}")
	public void sendMessage(@DestinationVariable("chatroom-uuid") String chatBase62RoomUUID,
			@Valid ChatMessageSendRequest request,
			@Header("simpSessionAttributes") Map<String, Object> sessionAttributes) {

		log.info(chatBase62RoomUUID);
		log.info(request.toString()+"---------");
		Long sendMemberNo = (Long) sessionAttributes.get("memberNo");

		chatMessageService.insertChatMessage(sendMemberNo, chatBase62RoomUUID, request);

	}
}