package com.spark.dating.dto.thread;

import java.util.Date;

import lombok.Data;

@Data
public class BoardReply {
  private int brNo; // 댓글 번호
  private String brContent; // 댓글 내용
  private Date createdAt; // 작성일자

  private int brThreadBoardNo; // 원글 번호
  private int brMemberNo; // 작성자 회원 번호
}