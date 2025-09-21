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

    return memberService.insertMember(member);
  }

  @GetMapping("/Member/SelectMemberByM_id")
  public Map<String, Object> SelectMemberByM_id(@RequestParam("m_id") String m_id) {
     return memberService.SelectMemberByM_id(m_id);
  }
}
