package com.spark.dating.dto.hearts;

import java.util.Date;

import lombok.Data;

@Data
public class Hearts {

	private int hNo;
	private int hSenduser; //보낸 유저
	private int hReceiveuser; //받은 유저
	private Date hDate;
	private char hRequestchanel; //경로 //f, m(feed or matching)
}
