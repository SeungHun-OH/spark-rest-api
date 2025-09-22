package com.spark.dating.dto.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatRoom {

	private int cr_no;
	private int cr_matchingNo;
	private String cr_name;

//	@Builder
//	public ChatRoom(String cr_name) {
//		this.cr_name = cr_name;
//	}
//
//	public void setCr_no(int cr_no) {
//		this.cr_no = cr_no;
//	}
//
//	public void setCr_matchingno(int cr_matchingno) {
//		this.cr_matchingNo = cr_matchingno;
//	}
//
//	@Override
//	public String toString() {
//		return "ChatRoom [cr_no=" + cr_no + ", cr_matchingno=" + cr_matchingNo + ", cr_name=" + cr_name + "]";
//	}
	
}
