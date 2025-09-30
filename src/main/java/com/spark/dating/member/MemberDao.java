package com.spark.dating.member;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.member.Member;

@Mapper
public interface MemberDao {
  int insertMember(Member member);

  Member SelectMemberByM_id(String mId); 
  
  int updateMember(Member member);
  
  int existsByNo(Long memberId);
}
