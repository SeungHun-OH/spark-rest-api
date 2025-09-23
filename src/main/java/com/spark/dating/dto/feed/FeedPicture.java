package com.spark.dating.dto.feed;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FeedPicture {

	private int fp_no;
	private int fp_feedno;
	
	private MultipartFile fp_attach;
	
	private String fp_attachtype;
	private String fp_attachoname;
	private byte[] fp_attachdata;
}