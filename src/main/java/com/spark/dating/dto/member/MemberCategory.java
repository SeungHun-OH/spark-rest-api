package com.spark.dating.dto.member;

import java.util.Date;

import lombok.Data;

@Data
public class MemberCategory {
  //MemberPreference 테이블명 참조 부모키만 가짐
  private int mc_no;
  private int mc_memberno;
  private int mc_preferno;
  private Date mc_createdat; 
}
