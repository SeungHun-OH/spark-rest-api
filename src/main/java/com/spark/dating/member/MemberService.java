package com.spark.dating.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.member.Member;

@Service
public class MemberService {
    @Autowired
    MemberDao memberDao;

    public int insertMember(Member member){
      return memberDao.insertMember(member);
    }

    public Member SelectMemberByM_id(String m_id){
      return memberDao.SelectMemberByM_id(m_id);
    }
    
}
