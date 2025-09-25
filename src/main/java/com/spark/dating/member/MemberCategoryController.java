package com.spark.dating.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.common.ApiResponse;
import com.spark.dating.dto.member.response.PreferenceCategory;
import com.spark.dating.dto.member.response.PreferenceRequest;


@RestController
public class MemberCategoryController {
  
  @Autowired
  MemberCategoryService memberCategoryService;

  @PostMapping("/member/categories")
  public void insertMemberCategories(@RequestBody PreferenceRequest request){
     memberCategoryService.insertMemberCategories(request.getMember_No(), request.getPreferNos());
  }

  @PostMapping("/member/categories/static")
  public List<PreferenceCategory> getAllCategoryStatic(){
    
    return memberCategoryService.getAllCategoryStatic();
  }
}
