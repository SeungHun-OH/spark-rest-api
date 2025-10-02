package com.spark.dating.dto.chat;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ChatMessageSend {

	@JsonIgnore
	private Long cmNo;

	@JsonIgnore
	private Long cmSendMember;

	@JsonIgnore
	private Long cmChatRoomNo;

	@NotBlank(message = "메세지를 입력해주세요.")
	private String cmMessage;
	
	@JsonIgnore
	@Builder.Default
	private LocalDateTime cmDate = LocalDateTime.now();

	public void setcmMessage(String cmMessage) {
		this.cmMessage = cmMessage;
	}

}
