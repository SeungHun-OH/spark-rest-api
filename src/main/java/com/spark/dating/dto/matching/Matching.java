package com.spark.dating.dto.matching;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Matching {
	private String name;
	private int age;
	private String region;
	private String bio;
	private String uuid;
	private String url;
	
	
	public void setMpNo(Long mpNo) {
		this.url = (mpNo != null) ? "/matching/picture/"+mpNo : null;
	}
	
}