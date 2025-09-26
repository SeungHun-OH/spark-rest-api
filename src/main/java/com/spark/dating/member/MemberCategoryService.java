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

  public int insertMemberCategories(int mp_memberNo, List<Integer> preferNos){
    int insertCount = 0;
    for(Integer preferNo : preferNos){
        memberCategoryDao.insertMemberCategories(mp_memberNo, preferNo);
        insertCount++;
    }
    return insertCount;
  }

  public List<PreferenceCategory> getAllCategoryStatic(){
    return memberCategoryDao.getAllCategoryStatic();
  }

  public PreferenceResponse getPreferenceByMember_No(int member_no){
    PreferenceResponse response = new PreferenceResponse();
    response.setMember_No(member_no);

    List<PreferenceCategory> selfPrefers = memberCategoryDao.selectSelfPrefers(member_no);
    response.setSelfPrefers(selfPrefers);
    
    // List<PreferenceCategory> partnerPrefers = memberCategoryDao.selectPartnerPrefers(member_no);
    // response.setPartnerPrefers(partnerPrefers);

    return response;
  }
}
