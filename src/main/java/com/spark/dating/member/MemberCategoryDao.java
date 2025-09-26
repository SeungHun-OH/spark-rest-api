package com.spark.dating.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spark.dating.dto.member.response.PreferenceCategory;

@Mapper
public interface MemberCategoryDao {
  int insertMemberCategories(@Param("mp_memberNo") int mp_memberNo,
      @Param("preferNo") int preferNo);

  List<PreferenceCategory> getAllCategoryStatic();
  List<PreferenceCategory> selectSelfPrefers(@Param("member_no") int member_no);
  // List<PreferenceCategory> selectPartnerPrefers(@Param("member_No") int member_no);
}
