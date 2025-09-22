package com.spark.dating.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.MemberPicture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
  @Autowired
  MemberService memberService;

  @Autowired
  JwtService jwtService;

  @PostMapping("member/create")
  public ApiResponse<Integer> Create(@RequestPart("member") Member member,
      @RequestPart("file") MultipartFile file) {
    return memberService.CreateMember(member, file);
  }

  @PostMapping("member/login")
  public Map<String, Object> login(@RequestBody Member memberlogin) {
    return memberService.login(memberlogin);
  }


  @PostMapping("/member")
  public ApiResponse<Integer> insertMember(@RequestBody Member member) {
    return memberService.insertMember(member);
  }

  @PostMapping("/member/picture")
  public ApiResponse<Integer> insertMemberPicture(@RequestParam("m_no") int m_no,
      @RequestParam("file") MultipartFile file) {
    return memberService.insertMemberPicture(m_no, file);
  }

  @GetMapping("/member")
  public ApiResponse<Member> SelectMemberByM_id(@RequestParam("m_id") String m_id) {
    return memberService.SelectMemberByM_id(m_id);
  }

  @GetMapping("/member/picture")
  public ApiResponse<MemberPicture> SelectMemberPictureByM_no(@RequestParam("m_no") int m_no) {

    return memberService.SelectMemberPictureByM_no(m_no);
  }
}
