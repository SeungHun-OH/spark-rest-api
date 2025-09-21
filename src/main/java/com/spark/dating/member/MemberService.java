package com.spark.dating.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.member.MemberPicture;

@Service
public class MemberService {

  @Autowired
  JwtService jwtService;

  @Autowired
  MemberDao memberDao;

  @Autowired
  MemberPictureDao memberPictureDao;

  public ApiResponse<Member> insertMember(Member member) {

    PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncode.encode(member.getM_password());
    member.setM_password(encodedPassword);
    try {
      memberDao.insertMember(member);
      return new ApiResponse<>("success", "회원 가입을 환영합니다", member);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public ApiResponse<MemberPicture> insertMemberPicture(int m_no, MultipartFile file) {
    try {
      MemberPicture memberPicture = new MemberPicture();

      memberPicture.setMp_memberno(m_no);
      memberPicture.setMp_attachoname(file.getOriginalFilename());
      memberPicture.setMp_attachtype(file.getContentType());
      memberPicture.setMp_attachdata(file.getBytes());

      memberPictureDao.insertMemberPicture(memberPicture);
      return new ApiResponse<>("success", "사진등록성공", null);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public Map<String, Object> login(@RequestBody Member memberlogin) {
    Map<String, Object> map = new HashMap<>();

    Member member = memberDao.SelectMemberByM_id(memberlogin.getM_id());
    if (member == null) {
      map.put("result", "fail");
      map.put("message", "아이디가 유효하지 않습니다");
    } else {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      map.put("member_pw", member.getM_password());
      map.put("memberlogin_pw", memberlogin.getM_password());
      boolean result = passwordEncoder.matches(memberlogin.getM_password(), member.getM_password());

      if (result) {
        String jwt = jwtService.createJWT(member.getM_id(), member.getM_email());

        map.put("result", "success");
        map.put("m_id", member.getM_id());
        map.put("m_name", member.getM_name());
        map.put("jwt", jwt);
        map.put("message", member.getM_name() + "님 환영합니다");
      } else {
        map.put("result", "fail");
        map.put("message", "비밀번호가 틀립니다");
      }
      map.put("data", member);
    }
    return map;
  }

  public ApiResponse<Member> SelectMemberByM_id(String m_id) {
    try {
      Member member = memberDao.SelectMemberByM_id(m_id);
      if (member == null) {
        return new ApiResponse<>("fail", "해당 아이디의 회원을 찾을 수 없습니다.", member);

      } else {
        return new ApiResponse<>("success", member.getM_name() + "회원님 로그인 환영합니다", member);
      }
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public ApiResponse<MemberPicture> SelectMemberPictureByM_no(int m_no) {
    try {
      MemberPicture memberPicture = memberPictureDao.SelectMemberPictureByM_no(m_no);
      if (memberPicture == null) {
        return new ApiResponse<>("fail", "해당 넘버의 사진을 찾을 수 없습니다.", memberPicture);

      } else {
        return new ApiResponse<>("success", "사진 조회 성공", memberPicture);
      }
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }
}
