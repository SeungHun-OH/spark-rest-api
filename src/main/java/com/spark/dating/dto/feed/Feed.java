package com.spark.dating.dto.feed;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Feed {
	/*
	 * json을 객체로 바꿀때 jackson이 등장 ->
	 * getter/setter을 만들때 기본적으로 소문자로 매핑 ->
	 * postman에서는 소문자로 해야 값이 전달 됨
	 */
	
	private int fNo;
	private int fMemberNo;
	private String fContent;
	private Date fDate;
	public int getfNo() {
		return fNo;
	}
	public void setfNo(int fNo) {
		this.fNo = fNo;
	}
	public int getfMemberNo() {
		return fMemberNo;
	}
	public void setfMemberNo(int fMemberNo) {
		this.fMemberNo = fMemberNo;
	}
	public String getfContent() {
		return fContent;
	}
	public void setfContent(String fContent) {
		this.fContent = fContent;
	}
	public Date getfDate() {
		return fDate;
	}
	public void setfDate(Date fDate) {
		this.fDate = fDate;
	}

	
}