package com.spark.dating.chat.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.chat.ChatMemberProfile;
import com.spark.dating.dto.chat.ChatMessage;
import com.spark.dating.dto.chat.ChatMessageSelect;
import com.spark.dating.dto.chat.ChatRoom;

@Mapper
public interface ChatMessageDao {
    
	public void createChatRoom(ChatRoom ChatRoom);
	public List<ChatRoom> selectAllChatRoom(int userNo);
	public void insertChatMessage(ChatMessage chatMessage);
	public List<ChatMessageSelect> getChattingMessage(Map<String, Object> chatMessageMap);
	public void updateLastMessage(ChatMessage chatMessage);
	public Long getOpponentMnoByChatRoom(Map<String, Object> chatMessageMap);
	public ChatMemberProfile getOpponentProfileByMno(Long opponentMno);
}
