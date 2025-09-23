package com.spark.dating.dto.feed;

import java.util.Date;

import lombok.Data;

@Data
public class Feed {
	private int f_no;
	private int f_memberno;
	private String f_content;
	private Date f_date;

	private FeedPicture feedPicture; //Mybatis association 매핑용
}