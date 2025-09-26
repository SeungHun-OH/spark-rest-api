package com.spark.dating.chat.service;

import java.util.List;
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

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;

	@Transactional
	public void createChatRoom(ChatRoomCreateRequest chatRoomCreateRequest) {
		if (chatRoomDao.isUserInMatching(chatRoomCreateRequest.getMatchingNo()) == 0) {
			throw new RestApiException(ChatErrorCode.USER_NOT_IN_MATCHING);
		}

		Long chatRoomNo = chatRoomDao.createChatRoom(UUID.randomUUID().toString().replace("-", ""));
		chatRoomDao.insertMatchingRoomMapping(MatchingRoomMapping.builder().mcMatchingNo(chatRoomCreateRequest.getMatchingNo())
				.mcChatRoomNo(chatRoomNo).build());
	}

	public List<ChatRoomSelectRequest> selectAllChatRoom(int userNo) {
		return chatRoomDao.selectAllChatRoom(userNo);
	}

	public ChatRoom select(int m_id) {
		return chatRoomDao.select(m_id);
	}

}
