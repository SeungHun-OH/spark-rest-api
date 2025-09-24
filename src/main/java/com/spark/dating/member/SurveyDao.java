package com.spark.dating.member;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.Survey;

@Mapper
public interface SurveyDao {
  int insertSurvey(Member member);

  Survey SelectSurveyByM_no(int m_no);

  int updateSurvey(Member member);

}
