package com.spark.dating.dto.member;

import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberPicture {

	// private int mpNo;
	// private int mpMemberNo;

	// private MultipartFile mpAttach;

	// private String mpAttachType;
	// private String mpAttachOname;
	// private byte[] mpAttachData;

	@JsonProperty("mpNo")
	private int mpNo;

	@JsonProperty("mpMemberNo")
	private int mpMemberNo;

	@JsonProperty("mpAttach")
	private MultipartFile mpAttach;

	@JsonProperty("mpAttachType")
	private String mpAttachType;

	@JsonProperty("mpAttachOname")
	private String mpAttachOname;

	@JsonProperty("mpAttachData")
	private byte[] mpAttachData;

}
