package com.spark.dating.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.chat.dao.ChatRoomDao;
import com.spark.dating.dto.chat.ChatRoom;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	public void createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {		
		chatRoomDao.createChatRoom(chatRoomCreateRequest.getMatchingNo());
	}

	public List<ChatRoom> selectAllChatRoom(int userNo) {
		return chatRoomDao.selectAllChatRoom(userNo);
	}

	public ChatRoom select(int m_id) {
		return chatRoomDao.select(m_id);
	}

}
