package com.spark.dating.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCategoryService {

  @Autowired
  private MemberCategoryDao memberCategoryDao;

  public String insertMemberCategories(int mp_memberNo, List<Integer> preferNos){
    for(Integer preferNo : preferNos){
        memberCategoryDao.insertMemberCategories(mp_memberNo, preferNo);
    }
    return "success";
  }
}
