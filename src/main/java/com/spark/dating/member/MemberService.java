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

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberService {

  @Autowired
  JwtService jwtService;

  @Autowired
  MemberDao memberDao;

  @Autowired
  MemberPictureDao memberPictureDao;

  public ApiResponse<Integer> CreateMember(Member member, MultipartFile file) {

    ApiResponse<Integer> response = insertMember(member);
    if (response.getData() == null) {
      return response;
    } else {
      ApiResponse<Integer> responsePicture = insertMemberPicture(member.getMNo(), file);
      return responsePicture;
    }
  }

  public ApiResponse<Integer> insertMember(Member member) {
    
    PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
    String encodedPassword = passwordEncode.encode(member.getMPassword());
    member.setMPassword(encodedPassword);
    try {
      memberDao.insertMember(member);
      return new ApiResponse<>("success", "회원 가입을 환영합니다" + member.getMNo() + "님", member.getMNo());
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public ApiResponse<Integer> insertMemberPicture(int mNo, MultipartFile file) {
    try {
      MemberPicture memberPicture = new MemberPicture();

      log.info("setMp_memberno(m_no)값은?" + mNo);
      memberPicture.setMpMemberNo(mNo);
      memberPicture.setMpAttachOname(file.getOriginalFilename()); 
      memberPicture.setMpAttachData(file.getBytes());
      memberPicture.setMpAttachType(file.getContentType());

      memberPictureDao.insertMemberPicture(memberPicture);
      return new ApiResponse<>("success", "사진등록성공", mNo);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), mNo);
    }
  }

  public Map<String, Object> login(@RequestBody Member memberlogin) {
    Map<String, Object> map = new HashMap<>();

    log.info("Login MemberService memberLogin값은?" + memberlogin);

    Member member = memberDao.SelectMemberByM_id(memberlogin.getMId());
    if (member == null) {
      map.put("result", "fail");
      map.put("message", "아이디가 유효하지 않습니다");
    } else {
      PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
      map.put("member_pw", member.getMPassword());
      map.put("memberlogin_pw", memberlogin.getMPassword());  
      boolean result = passwordEncoder.matches(memberlogin.getMPassword(), member.getMPassword());

      if (result) {
        String jwt = jwtService.createJWT(member.getMId(), member.getMEmail());

        map.put("result", "success");
        map.put("mId", member.getMId());
        map.put("mName", member.getMName());
        map.put("jwt", jwt);
        map.put("message", member.getMName() + "님 환영합니다");
        map.put("mNo", member.getMNo());
      } else {
        map.put("result", "fail");
        map.put("message", "비밀번호가 틀립니다");
      }
      map.put("data", member);
    }
    return map;
  }

  public ApiResponse<Member> SelectMemberByM_id(String mId) {
    try {
      Member member = memberDao.SelectMemberByM_id(mId);
      if (member == null) {
        return new ApiResponse<>("fail", "해당 아이디의 회원을 찾을 수 없습니다.", member);
      } else {
        return new ApiResponse<>("success", member.getMName() + "회원님 로그인 환영합니다", member);
      }
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public ApiResponse<MemberPicture> SelectMemberPictureByM_no(int mNo) {
    try {
      MemberPicture memberPicture = memberPictureDao.SelectMemberPictureByM_no(mNo);
      if (memberPicture == null) {
        return new ApiResponse<>("fail", "해당 넘버의 사진을 찾을 수 없습니다.", memberPicture);

      } else {
        return new ApiResponse<>("success", "사진 조회 성공", memberPicture);
      }
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  public ApiResponse<Integer> updateMember(Member member) {
    try {
      // PasswordEncoder passwordEncode = new BCryptPasswordEncoder();
      // String encodedPassword = passwordEncode.encode(member.getMPassword());
      // member.setMPassword(encodedPassword);  
      int updateCount = memberDao.updateMember(member);

      if (updateCount >= 1) {
        return new ApiResponse<>("success", "회원정보가 수정되었습니다", updateCount);
      } else {
        return new ApiResponse<>("fail", "수정할 회원이 존재하지 않습니다", 0);
      }
    } catch (Exception e) {
      return new ApiResponse<>("fail", "회원 수정 중 오류 발생" + e.getMessage(), 0);
    }
  }
}
