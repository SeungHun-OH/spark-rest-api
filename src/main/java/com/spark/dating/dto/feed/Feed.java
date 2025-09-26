package com.spark.dating.dto.feed;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Feed {
	/*
	 * json을 객체로 바꿀때 jackson이 등장 ->
	 * getter/setter을 만들때 기본적으로 소문자로 매핑 ->
	 * postman에서는 소문자로 해야 값이 전달 됨
	 */
	
	@JsonProperty("fNo")
	private int fNo;
	@JsonProperty("fMemberNo")
	private int fMemberNo;
	@JsonProperty("fContent")
	private String fContent;
	@JsonProperty("fDate")
	private Date fDate;
}