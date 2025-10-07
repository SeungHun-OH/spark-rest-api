package com.spark.dating.dto.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MemberStatusMessage {

	private MemberStatus memberStatus;
	@JsonIgnore
	private Long memberNO;
	private String memberUuid;
}
