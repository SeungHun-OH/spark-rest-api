package com.spark.dating.dto.matching;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Matching {
	private int mtNo;
	private int mtHeartsno;
	private Date mtDate;

	public int getMtNo() {
		return mtNo;
	}
	public int getMtHeartsno() {
		return mtHeartsno;
	}
	public Date getMtDate() {
		return mtDate;
	}
	public void setMtNo(int mtNo) {
		this.mtNo = mtNo;
	}
	public void setMtHeartsno(int mtHeartsno) {
		this.mtHeartsno = mtHeartsno;
	}
	public void setMtDate(Date mtDate) {
		this.mtDate = mtDate;
	}
}