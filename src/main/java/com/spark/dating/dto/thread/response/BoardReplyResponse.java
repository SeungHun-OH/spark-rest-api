package com.spark.dating.dto.thread.response;

import java.util.Date;

import lombok.Data;

@Data
public class BoardReplyResponse {
  private int brNo; // 댓글 번호
  private int brThreadBoardNo; // 원글 번호
  private int brMemberNo; // 작성자 회원 번호
  private String brContent; // 댓글 내용
  private Date createdAt; // 작성일자

  // 추가 정보 (조인해서 내려줌)
  private String memberName; // 작성자 닉네임
  private byte[] memberPicture; // 작성자 프로필 이미지 경로
}