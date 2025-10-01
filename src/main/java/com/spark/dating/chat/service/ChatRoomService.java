package com.spark.dating.chat.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.dating.chat.dao.ChatRoomDao;
import com.spark.dating.common.RestApiException;
import com.spark.dating.common.exception.ChatErrorCode;
import com.spark.dating.dto.chat.ChatRoom;
import com.spark.dating.dto.chat.ChatRoomCreateRequest;
import com.spark.dating.dto.chat.ChatRoomSelectRequest;
import com.spark.dating.dto.chat.MatchingRoomMapping;
import com.spark.dating.utils.UuidBase62Utils;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	@Transactional
	public void createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {

		if (chatRoomDao.existsMatchingNo(chatRoomCreateRequest.getMatchingNo()) != 1) {
			throw new RestApiException(ChatErrorCode.MATCHING_NOT_FOUND);
		}
		if (chatRoomDao.isValidMatchingUser(chatRoomCreateRequest.getMatchingNo()) != 1) {
			throw new RestApiException(ChatErrorCode.USER_NOT_IN_MATCHING);
		}
		chatRoomCreateRequest.setChatRoomUUID(UUID.randomUUID().toString().replace("-", ""));
		System.out.println(chatRoomCreateRequest.toString());
		chatRoomDao.createChatRoom(chatRoomCreateRequest);
		chatRoomDao.insertMatchingRoomMapping(
				MatchingRoomMapping.builder().mcMatchingNo(chatRoomCreateRequest.getMatchingNo())
						.mcChatRoomNo(chatRoomCreateRequest.getChatRoomNo()).build());
	}

	public List<ChatRoomSelectRequest> selectAllChatRoom(int memberNo) {
		List<ChatRoomSelectRequest> chatRoomList = ChatRoom.toDtoList(chatRoomDao.selectAllChatRoom(memberNo));
		return chatRoomList;
	}

	public Set<String> selectAllChatRoomUUID(String memberNo) {
		List<UUID> chatroomUuidList = chatRoomDao.selectAllChatRoomUUID(memberNo);
		if (chatroomUuidList == null || chatroomUuidList.isEmpty()) {
			return null;
		}
		Set<String> chatroomUuidBase62List = new HashSet<>();
		for (int i = 0; i < chatroomUuidList.size(); i++) {
			chatroomUuidBase62List.add(UuidBase62Utils.toBase62(chatroomUuidList.get(i)));
		}

		return chatroomUuidBase62List;
	}

}
