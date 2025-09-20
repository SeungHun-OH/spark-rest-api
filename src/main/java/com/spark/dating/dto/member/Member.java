package com.spark.dating.dto.member;

import lombok.Data;

@Data
public class Member {

	private int m_no;
	private String m_id;
	private String m_password;
	private String m_name;
	private String m_ssn;
	private int m_age;
	private String m_email;
	private char m_gender;
	private String m_phone;
	private String m_nickname;
	private String m_region;
	private String m_bio;
	private String m_mbti;
	private char m_active = 'T';

}
