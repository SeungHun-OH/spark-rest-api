package com.spark.dating.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

  // 회원정보 생성
  @PostMapping("member/create")
  public ApiResponse<Integer> Create(@RequestPart("member") Member member,
      @RequestPart("file") MultipartFile file) {

    log.info("Login MemberController CreateMember값은?" + member);

    return memberService.CreateMember(member, file);
  }

  // 회원정보 로그인
  @PostMapping("member/login")
  public Map<String, Object> login(@RequestBody Member memberlogin) {
    return memberService.login(memberlogin);
  }

  // 회원정보 수정
  @PutMapping("/member")
  public ApiResponse<Integer> UpdateMember(@RequestBody Member member) {
    log.info("멤버 업데이트" + member.toString());
    return memberService.updateMember(member);
  }

  @PostMapping("/member")
  public ApiResponse<Integer> insertMember(@RequestBody Member member) {

    // log.info("MemberController insertMember값은?" + member);
    // return new ApiResponse<>("fail", member.toString(), 1);

    // if(member.getMId() == ""){
    //   log.info("member가 비었습니다");
    //   return new ApiResponse<>("fail", "member가 비었습니다", 1);
    // }

    return memberService.insertMember(member);
  }

  @PostMapping("/member/picture")
  public ApiResponse<Integer> insertMemberPicture(@RequestParam("mNo") int mNo,
      @RequestParam("file") MultipartFile file) {
    return memberService.insertMemberPicture(mNo, file);
  }

  @GetMapping("/member")
  public ApiResponse<Member> SelectMemberByM_id(@RequestParam("mId") String mId) {
    return memberService.SelectMemberByM_id(mId);
  }

  @GetMapping("/member/picture")
  public ApiResponse<MemberPicture> SelectMemberPictureByM_no(@RequestParam("mNo") int mNo) {

    return memberService.SelectMemberPictureByM_no(mNo);
  }
}
