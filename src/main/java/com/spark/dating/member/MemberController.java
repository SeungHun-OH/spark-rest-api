package com.spark.dating.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.member.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
  @Autowired
  MemberService memberService;

  @Autowired
  JwtService jwtService;

  @PostMapping("/Member")
  public Map<String, Object> insertMember(@RequestBody Member member) {

    return memberService.insertMember(member);
  }

  @PostMapping("Member/picture")
  public Map<String, Object> insertMemberPicture(@RequestParam("m_no") int m_no,
                                                 @RequestParam("file") MultipartFile file) {
    return memberService.insertMemberPicture(m_no, file);
  }

  @GetMapping("/Member")
  public Map<String, Object> SelectMemberByM_id(@RequestParam("m_id") String m_id) {
    return memberService.SelectMemberByM_id(m_id);
  }
}
