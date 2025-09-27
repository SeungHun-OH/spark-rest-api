package com.spark.dating.chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatRoom;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.MatchingRoomMapping;

@Mapper
public interface ChatRoomDao {
    
	public long createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest);
	public int isValidMatchingUser(Long clientMatchingNo);
	public List<ChatRoom> selectAllChatRoom(Long memberNo);
	public void insertMatchingRoomMapping(MatchingRoomMapping matchingRoomMapping);
	public int existsMatchingNo(Long clientMatchingNo);
}
