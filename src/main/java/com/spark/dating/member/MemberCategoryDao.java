package com.spark.dating.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spark.dating.dto.member.response.PreferenceCategory;

@Mapper
public interface MemberCategoryDao {
  int insertMemberCategories(@Param("memberNo") int memberNo,
                             @Param("memberWho") String memberWho,
                             @Param("preferNo") int preferNo);

  List<PreferenceCategory> getAllCategoryStatic();

  List<PreferenceCategory> selectSelfPrefers(@Param("memberNo") int memberNo);
  List<PreferenceCategory> selectPartnerPrefers(@Param("memberNo") int memberNo);
}
