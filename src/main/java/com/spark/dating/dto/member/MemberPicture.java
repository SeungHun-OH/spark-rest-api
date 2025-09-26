package com.spark.dating.dto.member;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberPicture {

	private int mpNo;
	private int mpMemberNo;

	private MultipartFile mpAttach;

	private String mpAttachType;
	private String mpAttachOname;
	private byte[] mpAttachData;

}
