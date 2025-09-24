package com.spark.dating.member;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.PreferenceCategory;

@Mapper
public interface MemberCategoryDao {
  int insertPreference(Member member);

  PreferenceCategory SelectPreferenceByM_no(int m_no);

  int updatePreference(Member member);

}
