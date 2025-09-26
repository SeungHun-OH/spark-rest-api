package com.spark.dating.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberCategory {
  // MemberPreference 테이블명 참조 부모키만 가짐
  private int mcNo;
  private int mcMemberNo;
  private int mcPreferNo;
  private Date mcCreatedAt;
}
