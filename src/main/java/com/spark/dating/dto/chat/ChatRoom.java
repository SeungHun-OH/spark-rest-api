package com.spark.dating.dto.chat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spark.dating.utils.UuidBase62Utils;

import lombok.Getter;

@Getter
public class ChatRoom {
	private UUID crUUID;
	private String crName;
	private String crOpponentUuid;
	private String crMessage;
	private Integer crAge;
	private String crStatus;
	private LocalDateTime crDate;
	private int crUnreadCount;
	@JsonIgnore
	private String crAttachType;
	private String crAttachData;

	
	public ChatRoomSelectResponse toDto() {
		return ChatRoomSelectResponse.builder().encodedUUID(UuidBase62Utils.toBase62(crUUID)).chatRoomName(crName).opponentUuid(crOpponentUuid).lastMessage(crMessage).age(crAge).status(crStatus).unreadCount(crUnreadCount)
				.date(crDate).imgData(crAttachData).build();
	}
	
	
	public void setCrAttachData(byte[] AttachData) {
		if (crAttachData != null) {
			String base64 = Base64.getEncoder().encodeToString(AttachData);
			this.crAttachData = "data:" + this.crAttachType + ";base64," + base64;
		}
	}
	
	public static List<ChatRoomSelectResponse> toDtoList(List<ChatRoom> voList) {
		List<ChatRoomSelectResponse> dtoList = new ArrayList<>();
		for (ChatRoom vo : voList) {
			dtoList.add(vo.toDto());
		}
		return dtoList;
	}
}
