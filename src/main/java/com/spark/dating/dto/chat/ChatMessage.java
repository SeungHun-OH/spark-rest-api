package com.spark.dating.dto.chat;

import java.util.Date;

import lombok.Data;

@Data
public class ChatMessage {

	private int cm_no;
	private int cm_senduser;
	private int cm_chatlistno;
	private String cm_msg;
	private Date cm_date;
}
