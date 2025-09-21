package com.spark.dating.member;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.member.MemberPicture;

@Mapper
public interface MemberPictureDao {
  int insertMemberPicture(MemberPicture memberPicture);
}
