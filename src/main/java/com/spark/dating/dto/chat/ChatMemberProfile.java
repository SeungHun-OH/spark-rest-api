package com.spark.dating.dto.chat;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ChatMemberProfile {

	@JsonProperty("opponentUuid")
	private String mpUUID;

	private String mpName;

	private int mpAge;

	@JsonProperty("nickName")
	private String mpNickName;
		
	@JsonIgnore
	private String mpAttachType;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty("attachData")
	private String mpAttachData;

	@JsonProperty("status")
	private String mpStatus;


	public void setMpAttachData(byte[] mpAttachData) {
		if (mpAttachData != null) {
			String base64 = Base64.getEncoder().encodeToString(mpAttachData);
			this.mpAttachData = "data:" + this.mpAttachType + ";base64," + base64;
		}
	}

	@JsonProperty("name")
	public String getmpName() {
		return mpName;
	}

	@JsonProperty("age")
	public int getmpAge() {
		return mpAge;
	}

}
