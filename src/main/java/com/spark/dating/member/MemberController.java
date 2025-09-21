package com.spark.dating.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
  @Autowired
  MemberService memberService;

  @Autowired
  JwtService jwtService;

  @PostMapping("/Member/insert")
  public Map<String, Object> insertMember(@RequestBody Member member) {

    Map<String, Object> map = new HashMap<>();

    PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncode.encode(member.getM_password());
    member.setM_password(encodedPassword);

    int memberCount = memberService.insertMember(member);
    try {
      map.put("result", "success");
      map.put("Message", "회원님 가입을 환영합니다");
      return map;
    } catch (Exception e) {

      map.put("result", "fail");
      map.put("Message", e.getMessage());
      return map;
    }
  }

  @GetMapping("/Member/SelectMemberByM_id")
  public Map<String, Object> SelectMemberByM_id(@RequestParam("m_id") String m_id) {
    Map<String, Object> map = new HashMap<>();

    try {
      Member member = memberService.SelectMemberByM_id(m_id);
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
