package com.spark.dating.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.MemberPicture;
import com.spark.dating.dto.member.request.MemberLoginRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberController {
  @Autowired
  MemberService memberService;

  @Autowired
  JwtService jwtService;

  // 회원 생성
  @PostMapping("member/create")
  public ApiResponse<Integer> create(@RequestPart("member") Member member, @RequestPart("file") MultipartFile file) {
    return memberService.createMember(member, file);
  }

  //회원 로그인
  @PostMapping("member/login")
  public Map<String, Object> login(@RequestBody MemberLoginRequest memberlogin) {
    Map<String, Object> map = memberService.login(memberlogin);
    return map;
  }

  // 회원 수정
  @PutMapping("/member")
  public ApiResponse<Integer> updateMember(@RequestBody Member member) {
    log.info("멤버 업데이트" + member.toString());
    return memberService.updateMember(member);
  }

  // 회원 등록
  @PostMapping("/member")
  public ApiResponse<Integer> insertMember(@RequestBody Member member) {
    return memberService.insertMember(member);
  }

  // 회원 사진등록
  @PostMapping("/member/picture")
  public ApiResponse<Integer> insertMemberPicture(@RequestParam("mNo") int mNo,
      @RequestParam("file") MultipartFile file) {
    return memberService.insertMemberPicture(mNo, file);
  }

  // 회원 조회(단일)
  @GetMapping("/member")
  public ApiResponse<Member> selectMemberByM_id(@RequestParam("mId") String mId) {
    ApiResponse<Member> response = memberService.selectMemberByM_id(mId);

    log.info("로그인 데이터 머가 들어오니" + response.getData().toString());

    return response;
  }

  // 회원 사진 조회(단일))
  @GetMapping("/member/picture")
  public ApiResponse<MemberPicture> selectMemberPictureByM_no(@RequestParam("mNo") int mNo) {
    return memberService.selectMemberPictureByM_no(mNo);
  }

  // jwt 토큰으로 회원정보 조회
  // @GetMapping("member/jwt")
  // public ApiResponse<Member> selectMemberByJwt(@RequestParam("jwt") String jwt)
  // {
  // return memberService.selectMemberByJwt(jwt);
  // }

  // jwt 토큰으로 회원정보 조회
  @GetMapping("/member/getjwt")
  public ApiResponse<Map<String, String>> selectMemberByJwt(@RequestHeader("Authorization") String authHeader) {
    // "Bearer eyJ..." 에서 "Bearer " 제거
  
    return memberService.selectMemberByJwt(authHeader);
  }

  @GetMapping("/member/test")
  @ResponseBody
  public String Test() throws JsonProcessingException {
    Member member = new Member();
    member.setMEmail("www@www.www");
    return new ObjectMapper().writeValueAsString(member);
  }
}

// log.info("MemberController insertMember값은?" + member);
// return new ApiResponse<>("fail", member.toString(), 1);

// if(member.getMId() == ""){
// log.info("member가 비었습니다");
// return new ApiResponse<>("fail", "member가 비었습니다", 1);
// }
 // -------------------------승훈님 













 // -------------------------주희님   