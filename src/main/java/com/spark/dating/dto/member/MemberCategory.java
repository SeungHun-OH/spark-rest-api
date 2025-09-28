package com.spark.dating.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberCategory {
  private int  mcNo;
  private int  mcMemberNo;
  private int  mcPreferNo;
  private char mcWho;
  private Date mcCreatedAt;
}
