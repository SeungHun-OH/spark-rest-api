package com.spark.dating.dto.member;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberPicture {
	
	private int mp_no;
	private int mp_memberno;
	
	private MultipartFile mp_attach;
	
	private String mp_attachtype;
	private String mp_attachoname;
	private byte[] mp_attachdata;
}
