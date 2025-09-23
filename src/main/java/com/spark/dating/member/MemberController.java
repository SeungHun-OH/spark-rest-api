package com.spark.dating.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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

  //url
  @PostMapping("/Member")
  public ApiResponse<Member> insertMember(@RequestBody Member member) {
    return memberService.insertMember(member);
  }

  @GetMapping("/Member")
  public ApiResponse<Member> SelectMemberByM_id(@RequestParam("m_id") String m_id) {
    return memberService.SelectMemberByM_id(m_id);
  }

  @PostMapping("Member/login")
  public Map<String, Object> login(@RequestBody Member memberlogin) {

    return memberService.login(memberlogin);
  }

  @PostMapping("/Member/picture")
  public ApiResponse<MemberPicture> insertMemberPicture(@RequestParam("m_no") int m_no,
      @RequestParam("file") MultipartFile file) {
    return memberService.insertMemberPicture(m_no, file);
  }

  @GetMapping("/Member/picture")
  public ApiResponse<MemberPicture> SelectMemberPictureByM_no(@RequestParam("m_no") int m_no) {

    ApiResponse<MemberPicture> response = memberService.SelectMemberPictureByM_no(m_no);

    log.info(response.getMessage());
    log.info(response.getData().getMp_attachtype());

    return memberService.SelectMemberPictureByM_no(m_no);
  }
}
