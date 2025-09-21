package com.spark.dating.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.member.Member;

@Service
public class MemberService {

  @Autowired
  JwtService jwtService;

  @Autowired
  MemberDao memberDao;

  public int insertMember(Member member) {
    return memberDao.insertMember(member);
  }


  public  Map<String, Object> SelectMemberByM_id(String m_id) {
    Map<String, Object> map = new HashMap<>();
    try {
      Member member = memberDao.SelectMemberByM_id(m_id);
      if (member == null) {
        map.put("result", "fail");
        map.put("member", member);
        map.put("Message", "해당 아이디의 회원을 찾을 수 없습니다.");
      } else {
        map.put("result", "success");
        map.put("member", member);
        map.put("Message", member.getM_name() + "회원님 로그인 환영합니다");
      }
      return map;
    } catch (Exception e) {
      map.put("result", "fail");
      map.put("Message", e.getMessage());
      return map;
    }
  }
}
