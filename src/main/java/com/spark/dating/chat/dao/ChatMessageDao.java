package com.spark.dating.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatMessageSelectRequest;
import com.spark.dating.dto.chat.ChatMessageSend;
import com.spark.dating.dto.chat.ChatRoom;

@Mapper
public interface ChatMessageDao {
    
	public void createChatRoom(ChatRoom ChatRoom);
	public List<ChatRoom> selectAllChatRoom(int userNo);
	public void insertChatMessage(ChatMessageSend chatMessageSend);
	public List<ChatMessageSelectRequest> getChattingMessage(String chatRoomUUID);
}
