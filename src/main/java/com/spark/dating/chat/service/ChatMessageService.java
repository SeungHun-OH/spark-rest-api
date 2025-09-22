package com.spark.dating.chat.service;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageService {

	private final SimpMessagingTemplate  template;


	public void sendMessage(String message) {
		template.convertAndSend("/sub/chat/",message);
	}

	
}
