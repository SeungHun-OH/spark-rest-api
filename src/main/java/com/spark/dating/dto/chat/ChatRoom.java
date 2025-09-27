package com.spark.dating.dto.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.spark.dating.utils.UuidBase62Utils;

import lombok.Getter;

@Getter
public class ChatRoom {
	private UUID crUUID;
	private String crName;
	private String crMessage;
	private Integer crAge;
	private String crDate;

	public ChatRoomSelectRequest toDto() {
		return ChatRoomSelectRequest.builder().encodedUUID(UuidBase62Utils.toBase62(crUUID)).chatRoomName(crName).lastMessage(crMessage).age(crAge)
				.date(crDate).build();
	}

	public static List<ChatRoomSelectRequest> toDtoList(List<ChatRoom> voList) {
		List<ChatRoomSelectRequest> dtoList = new ArrayList<>();
		for (ChatRoom vo : voList) {
			dtoList.add(vo.toDto());
		}
		return dtoList;
	}
}
