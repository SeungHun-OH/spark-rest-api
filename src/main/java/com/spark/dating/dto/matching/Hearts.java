package com.spark.dating.dto.matching;

import java.util.Date;

import lombok.Data;

@Data
public class Hearts {

	private int h_no;
	private int h_senduser;
	private int h_receiveuser;
	private Date h_date;
	private char h_requestchanel;
}
