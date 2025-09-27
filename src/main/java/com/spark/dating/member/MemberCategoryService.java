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

  public int insertMemberCategories(int memberNo, String memberWho, List<Integer> preferNos){
    int insertCount = 0;
    for(Integer preferNo : preferNos){
        memberCategoryDao.insertMemberCategories(memberNo, memberWho, preferNo);
        insertCount++;
    }
    return insertCount;
  }

  public List<PreferenceCategory> getAllCategoryStatic(){
    return memberCategoryDao.getAllCategoryStatic();
  }

  public PreferenceResponse getPreferenceByMember_No(int memberNo){
    PreferenceResponse response = new PreferenceResponse();
    response.setMemberNo(memberNo);

    List<PreferenceCategory> selfPrefers = memberCategoryDao.selectSelfPrefers(memberNo);
    response.setSelfPrefers(selfPrefers);
    
    List<PreferenceCategory> partnerPrefers = memberCategoryDao.selectPartnerPrefers(memberNo);
    response.setPartnerPrefers(partnerPrefers);

    return response;
  }
}
