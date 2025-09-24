package com.spark.dating.dto.member;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class MemberCategory {
  //MemberPreference 테이블명 참조 부모키만 가짐
  private int mp_no;
  private int mp_memberno;
  private int mp_preferno;
  private Date created_at; 

  List<PreferenceCategory>SelfPrefers;
  List<PreferenceCategory>PartnerPrefers;

}
