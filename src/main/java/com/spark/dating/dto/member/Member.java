package com.spark.dating.dto.member;

import lombok.Data;

@Data
public class Member {

	private int mNo;
	private String mId;
	private String mPassword;
	private String mName;
	private String mSsn;
	private int mAge;
	private String mEmail;
	private char mGender;
	private String mPhone;
	private String mNickname;
	private String mRegion;
	private String mBio;
	private String mMbti;
	private char mActive = 'T';

}
