package com.spark.dating.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.member.response.PreferenceCategory;
import com.spark.dating.dto.member.response.PreferenceResponse;

@Service
public class MemberCategoryService {

  @Autowired
  private MemberCategoryDao memberCategoryDao;

  // 멤버 카테고리 등록 여러개 등록
  public int insertMemberCategories(int memberNo, String memberWho, List<Integer> preferNos) {
    int insertCount = 0;
    for (Integer preferNo : preferNos) {
      memberCategoryDao.insertMemberCategories(memberNo, memberWho, preferNo);
      insertCount++;
    }
    return insertCount;
  }

  // 카테고리 목록 조회(Static)
  public List<PreferenceCategory> getAllCategoryStatic() {
    return memberCategoryDao.getAllCategoryStatic();
  }

  // 멤버 선택 카테고리 조회 (memberNo 참조키 / selfPrefers 배열 / partnerPrefers 배열)
  public PreferenceResponse getPreferenceByMember_No(int memberNo) {
    PreferenceResponse response = new PreferenceResponse();
    response.setMemberNo(memberNo);

    List<PreferenceCategory> selfPrefers = memberCategoryDao.selectSelfPrefers(memberNo);
    response.setSelfPrefers(selfPrefers);

    List<PreferenceCategory> partnerPrefers = memberCategoryDao.selectPartnerPrefers(memberNo);
    response.setPartnerPrefers(partnerPrefers);

    return response;
  }

  // 멤버 전체 카테고리 삭제
  public int deleteCategoriesByMemberWho(int memberNo, String memberWho) {
    return memberCategoryDao.deleteCategoriesByMemberWho(memberNo, memberWho);
  }

  // 취미 카테고리 조회
  public List<PreferenceCategory> getHobbyCategoriesByMemberNo(int memberNo) {
    return memberCategoryDao.selectHobbyCategoriesByMemberNo(memberNo);
  }
}
