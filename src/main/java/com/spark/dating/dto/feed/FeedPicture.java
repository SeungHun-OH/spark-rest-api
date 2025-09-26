package com.spark.dating.dto.feed;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class FeedPicture {

	private int fpNo;
	private int fpFeedNo;

	private MultipartFile fpAttach;

	private String fpAttachtype;
	private String fpAttachoname;
	private byte[] fpAttachdata;

	static public FeedPicture insertFeedPictures(int f_no, MultipartFile file) {
		FeedPicture feedPicture = new FeedPicture();
		try {
			feedPicture.setFpFeedNo(f_no);
			feedPicture.setFpAttachoname(file.getOriginalFilename());
			feedPicture.setFpAttachdata(file.getBytes());
			feedPicture.setFpAttachtype(file.getContentType());
		} catch (IOException e) {
			log.info("error");
		}
		return feedPicture;
	}
}