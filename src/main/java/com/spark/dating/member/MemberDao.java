package com.spark.dating.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spark.dating.dto.chat.MemberStatusMessage;
import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.MemberForFeed;

@Mapper
public interface MemberDao {
  int insertMember(Member member);

  Member SelectMemberByM_id(String mId); 
  
  int updateMember(Member member);
  
  int existsByNo(Long memberId);

  MemberForFeed selectMemberByMnickname(String m_nickname);
  
  void updateMemberStatusInfo(MemberStatusMessage memberStatus);
  
  String selectMnickNameByMno (Long memberNo);
  
  int checkUuidMatch(Map<String, Object> memberNoAndUuidMap);
  
  Long getMemberNoByUuid(String uuid);
  
  String getMemberUuidByMemberNo(Long memberNo);

  MemberForFeed selectMemberByMno(int mNo);

  List<MemberForFeed> selectRandomMembersExceptMe(int myNo, int count);
  
  String selectMemberGenderByMno(int memberNo);

  @Select("SELECT * FROM MEMBER ORDER BY DBMS_RANDOM.VALUE FETCH FIRST 1 ROWS ONLY")
  Member getRandomMember();
}
