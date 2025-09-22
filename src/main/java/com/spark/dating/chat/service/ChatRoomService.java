package com.spark.dating.chat.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.chat.dao.ChatRoomDao;
import com.spark.dating.dto.chat.ChatRoom;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;
	
	public void createChatRoom(int h_no, String cr_name) {
//		ChatRoom chatRoom = ChatRoom.builder().cr_name(cr_name).build();
//		chatRoom.setCr_matchingno(h_no);
//		System.out.println(chatRoom.toString());
//		chatRoomDao.createChatRoom(chatRoom);
		chatRoomDao.createChatRoom(new ChatRoom(1,1,"홍길동"));
	}
	
	public List<ChatRoom> selectAllChatRoom(int m_id){
		return chatRoomDao.selectAllChatRoom(m_id);
	}
	public ChatRoom select(int m_id){
		return chatRoomDao.select(m_id);
	}
	
}
