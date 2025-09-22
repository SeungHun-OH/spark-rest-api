package com.spark.dating.chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatMessageService;
import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.dto.chat.ChatRoom;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class ChatController {

	@Autowired
	private ChatRoomService chatRoomService;
	@Autowired
	private ChatMessageService chatMessageService;

	@PostMapping("/rooms")
	public String createChatRoom(@RequestParam("h_no") int h_no, @RequestParam("cr_name") String cr_name) {
		chatRoomService.createChatRoom(h_no, cr_name);
		return "SUCCESS";
	}

	@GetMapping("/rooms")
	public Map<String, Object> findAllChatRoom(@RequestParam("m_id") int m_id) {
		// 추후에 로그인한 유저번호를 가지고 조인을 하던 값을 얻어야 할듯
		System.out.println("여기");
		Map<String, Object> resultMap = new HashMap<>();
		List<ChatRoom> chatRooms = chatRoomService.selectAllChatRoom(m_id);
//		ChatRoom cr = chatRoomService.select(1);
//		System.out.println(cr.toString());
		resultMap.put("Success", chatRooms);

		return resultMap;
	}

	@MessageMapping("/{roomId}")
	@SendTo("/room/{roomId")
	public void sendMessage(int roomId, String message) {
		System.out.println("연결");
		chatMessageService.sendMessage(message);
	}
}