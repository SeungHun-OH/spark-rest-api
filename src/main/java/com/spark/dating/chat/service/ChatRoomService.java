package com.spark.dating.chat.service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import com.spark.dating.dto.chat.ChatRoomSelectResponse;
import com.spark.dating.dto.chat.MatchingRoomMapping;
import com.spark.dating.dto.hearts.HeartsRequest;
import com.spark.dating.hearts.HeartsService;
import com.spark.dating.utils.UuidBase62Utils;

@Service
public class ChatRoomService {

	@Autowired
	private ChatRoomDao chatRoomDao;
	
	@Autowired
	private HeartsService heartsService;

	@Transactional
	public void createChatRoom(Long heartsNo, int memberNo) {
		// heart요청을 승낙한 값을 저장할 객체
		HeartsRequest heartsRequest = new HeartsRequest();
		heartsRequest.setHeartsNo(heartsNo);
		
		// 해당 쿼리를 수행하면 matchingNo 바인딩 됨
		heartsService.acceptHeartRequest(heartsRequest);
		
		// 최종적으로 채팅방을 위한 값들을 저장할 객체, 해당 부분에 매칭번호가 필요하기 때문에 위에서 pk를 바인딩 시키고 처리해야함
		// 매칭 번호가 없으면 채팅방이 만들어지면 안되기 때문에 트랜잭션 처리
		ChatRoomCreateRequest chatRoomCreateRequest = ChatRoomCreateRequest.builder().memberNo(heartsNo).chatRoomUUID(UUID.randomUUID().toString().replace("-", "")).matchingNo(heartsRequest.getMatchingNo()).build(); 
		
		if (chatRoomDao.existsMatchingNo(chatRoomCreateRequest.getMatchingNo()) != 1) {
			throw new RestApiException(ChatErrorCode.MATCHING_NOT_FOUND);
		}
		if (chatRoomDao.isValidMatchingUser(chatRoomCreateRequest.getMatchingNo()) != 1) {
			throw new RestApiException(ChatErrorCode.USER_NOT_IN_MATCHING);
		}
		
		System.out.println(chatRoomCreateRequest.toString());
		chatRoomDao.createChatRoom(chatRoomCreateRequest);
		chatRoomDao.insertMatchingRoomMapping(
				MatchingRoomMapping.builder().mcMatchingNo(chatRoomCreateRequest.getMatchingNo())
						.mcChatRoomNo(chatRoomCreateRequest.getChatRoomNo()).build());
	}

	public List<ChatRoomSelectResponse> selectAllChatRoom(int memberNo) {
		List<ChatRoomSelectResponse> chatRoomList = ChatRoom.toDtoList(chatRoomDao.selectAllChatRoom(memberNo));
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

	public boolean existsChatroomByMemberNoAndUuid(Map<String, Object> memberNoAndUuid) {
		return chatRoomDao.existsChatroomByMemberNoAndUuid(memberNoAndUuid) == 1 ? true : false;
	}

	public Long findChatRoomIdByUuid(String chatRoomUUID) {
		
		return chatRoomDao.findChatRoomIdByUuid(chatRoomUUID);
	}
	
	@Transactional
	public void leaveChatRoom(String chatBase62RoomUUID) {
		String chatRoomUUID = UuidBase62Utils.fromBase62(chatBase62RoomUUID).toString().replace("-", "").toUpperCase();
		Long chatRoomNo = findChatRoomIdByUuid(chatRoomUUID);
		
		chatRoomDao.deleteMatchingChatRoomMapping(chatRoomNo);
		chatRoomDao.deleteChatMessages(chatRoomNo);
		chatRoomDao.deleteChatRoom(chatRoomNo);
	}

}
