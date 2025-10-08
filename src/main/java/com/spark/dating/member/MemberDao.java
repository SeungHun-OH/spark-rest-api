package com.spark.dating.member;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.MemberForFeed;

@Mapper
public interface MemberDao {
  int insertMember(Member member);

  Member SelectMemberByM_id(String mId); 
  
  int updateMember(Member member);
  
  int existsByNo(Long memberId);

  MemberForFeed selectMemberByMnickname(String m_nickname);

  MemberForFeed selectMemberByMno(int mNo);

  List<MemberForFeed> selectRandomMembersExceptMe(int myNo, int count);
}
