package com.spark.dating.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatRoom;
import com.spark.dating.dto.chat.ChatRoomSelectRequest;
import com.spark.dating.dto.chat.MatchingRoomMapping;

@Mapper
public interface ChatRoomDao {
    
	public Long createChatRoom(String chatRoomUUID);
	public int isUserInMatching(Long clientMatchingNo);
	public List<ChatRoomSelectRequest> selectAllChatRoom(int userNo);
	public ChatRoom select(int m_id);
	public void insertMatchingRoomMapping(MatchingRoomMapping matchingRoomMapping);
}
