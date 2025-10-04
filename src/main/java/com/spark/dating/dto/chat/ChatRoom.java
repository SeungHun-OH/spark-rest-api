package com.spark.dating.dto.chat;

import java.time.LocalDateTime;
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
	private LocalDateTime crDate;
	private int crUnreadCount;

	public ChatRoomSelectResponse toDto() {
		return ChatRoomSelectResponse.builder().encodedUUID(UuidBase62Utils.toBase62(crUUID)).chatRoomName(crName).lastMessage(crMessage).age(crAge).unreadCount(crUnreadCount)
				.date(crDate).build();
	}

	public static List<ChatRoomSelectResponse> toDtoList(List<ChatRoom> voList) {
		List<ChatRoomSelectResponse> dtoList = new ArrayList<>();
		for (ChatRoom vo : voList) {
			dtoList.add(vo.toDto());
		}
		return dtoList;
	}
}
