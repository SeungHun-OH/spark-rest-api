package com.spark.dating.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatRoom;

@Mapper
public interface ChatRoomDao {
    
	public void createChatRoom(int matchingNO);
	public List<ChatRoom> selectAllChatRoom(int userNo);
	public ChatRoom select(int m_id);
}
