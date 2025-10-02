package com.spark.dating.dto.hearts;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

//@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Hearts {
	@JsonIgnore //client가 보내지 못하는 것
	private int hNo;
	private Integer hSendUser; //보낸 유저
	private int hReceiveUser; //받은 유저
	private Date hDate;
	private String hRequestChanel; //경로 //f, m(feed or matching)	
	
	public int gethNo() {
		return hNo;
	}
	public Integer gethSendUser() {
		return hSendUser;
	}
	public int gethReceiveUser() {
		return hReceiveUser;
	}
	public Date gethDate() {
		return hDate;
	}
	public String gethRequestChanel() {
		return hRequestChanel;
	}
	public void sethNo(int hNo) {
		this.hNo = hNo;
	}
	public void sethSendUser(Integer hSendUser) {
		this.hSendUser = hSendUser;
	}
	public void sethReceiveUser(int hReceiveUser) {
		this.hReceiveUser = hReceiveUser;
	}
	public void sethDate(Date hDate) {
		this.hDate = hDate;
	}
	public void sethRequestChanel(String hRequestChanel) {
		this.hRequestChanel = hRequestChanel;
	}
}
