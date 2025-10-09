package com.spark.dating.dto.login;

import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class AuthMember {

	private Long mNo;
	private String mId;
	private String mPassword;
	private String mName;
	private String mUuid;	
	
}
