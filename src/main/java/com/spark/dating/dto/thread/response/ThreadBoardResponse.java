package com.spark.dating.dto.thread.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonAutoDetect(
  fieldVisibility = JsonAutoDetect.Visibility.ANY, 
  getterVisibility = JsonAutoDetect.Visibility.NONE, 
  isGetterVisibility = JsonAutoDetect.Visibility.NONE, 
  setterVisibility = JsonAutoDetect.Visibility.NONE)

@Data
public class ThreadBoardResponse {

  //ThreadBoard
  private int tbNo;
  private String tbTitle;
  private String tbContent;
  private String tbDate;

  private String tbActive;

  private int tbLikeCount;
  private int tbMemberNo;
  private int tbViewCount;
  private Integer tbImageNo;

  private Date createdAt;
  private Date updatedAt;

  //Member 
  private String memberId;
  private String memberName;

  //MemberPicture
  private byte[] memberPicture;
}
