package com.spark.dating.dto.chat;

import java.util.Date;

import lombok.Data;

@Data
public class ChatingData {

	private int c_no;
	private int c_senduser;
	private int c_chatlistno;
	private String c_msg;
	private Date c_date;
}
