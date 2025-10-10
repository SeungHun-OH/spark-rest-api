package com.spark.dating.dto.chat;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessageWithMemberResponse {

	@JsonProperty("profile")
	private ChatMemberProfile chatMemberProfile;
	
	@JsonProperty("chatMessages")
	private List<ChatMessageSelect> chatMessages;
}
