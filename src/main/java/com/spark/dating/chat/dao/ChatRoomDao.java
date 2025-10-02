package com.spark.dating.chat.dao;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatRoom;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.MatchingRoomMapping;

@Mapper
public interface ChatRoomDao {
    
	public long createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest);
	public int isValidMatchingUser(Long clientMatchingNo);
	public List<ChatRoom> selectAllChatRoom(int memberNo);
	public void insertMatchingRoomMapping(MatchingRoomMapping matchingRoomMapping);
	public int existsMatchingNo(Long clientMatchingNo);
	public List<UUID> selectAllChatRoomUUID(String memberNo);
	public int existsChatroomByMemberNoAndUuid(Map<String,Object> memberNoAndUuid);
	public Long findChatRoomIdByUuid(String chatRoomUUID);
}
