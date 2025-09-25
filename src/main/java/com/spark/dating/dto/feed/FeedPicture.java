package com.spark.dating.dto.feed;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class FeedPicture {

	private int fp_no;
	private int fp_feedno;

	private MultipartFile fp_attach;

	private String fp_attachtype;
	private String fp_attachoname;
	private byte[] fp_attachdata;

	static public FeedPicture insertFeedPictures(int f_no, MultipartFile file) {
		FeedPicture feedPicture = new FeedPicture();
		try {
			feedPicture.setFp_feedno(f_no);
			feedPicture.setFp_attachoname(file.getOriginalFilename());
			feedPicture.setFp_attachdata(file.getBytes());
			feedPicture.setFp_attachtype(file.getContentType());
		} catch (IOException e) {
			log.info("error");
		}
		return feedPicture;
	}
}