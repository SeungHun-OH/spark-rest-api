package com.spark.dating.member;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
    } 
    catch (Exception e) {
      return new ApiResponse<>("fail",e.getMessage(),null);
    }
  }

  public Map<String, Object> insertMemberPicture(int m_no, MultipartFile file){
    Map<String, Object>map = new HashMap<>();
    try{
      MemberPicture memberPicture = new MemberPicture();

      memberPicture.setMp_memberno(m_no);
      memberPicture.setMp_attachoname(file.getOriginalFilename());
      memberPicture.setMp_attachtype(file.getContentType());
      memberPicture.setMp_attachdata(file.getBytes());

    }catch(Exception e){
      
    }

    return map;
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
