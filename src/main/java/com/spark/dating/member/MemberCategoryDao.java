package com.spark.dating.member;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberCategoryDao {
  int insertMemberCategories(@Param("mp_memberNo") int mp_memberNo,
                             @Param("preferNo") int preferNo);
}
