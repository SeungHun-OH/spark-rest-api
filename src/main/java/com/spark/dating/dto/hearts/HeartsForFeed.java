package com.spark.dating.dto.hearts;


import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HeartsForFeed {
	
	private int hNo;
	private String hSendUser;
	private String hReceiveUser;
	private Date hDate;
	private char h_requestchanel; //request
	private String h_status;
}
