package com.spark.dating.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.member.response.PreferenceCategory;
import com.spark.dating.dto.member.response.PreferenceRequest;
import com.spark.dating.dto.member.response.PreferenceResponse;

@RestController
public class MemberCategoryController {

  @Autowired
  MemberCategoryService memberCategoryService;

  @PostMapping("/member/categories")
  public ApiResponse<Integer> insertMemberCategories(@RequestBody PreferenceRequest request) {
    try {
      int insertedCount = memberCategoryService.insertMemberCategories(request.getMember_No(), request.getPreferNos());
      return new ApiResponse<Integer>("success", insertedCount + "개의 카테고리 저장 완료", insertedCount);
    } catch (Exception e) {
      return new ApiResponse<Integer>("fail", e.getMessage(), null);
    }
  }

  @PostMapping("/member/categories/static")
  public List<PreferenceCategory> getAllCategoryStatic() {

    return memberCategoryService.getAllCategoryStatic();
  }

  // public ApiResponse<Member> SelectMemberByM_id
  // (@RequestParam("m_id") String m_id) {
  @GetMapping("/member/membercategories")
  public ApiResponse<PreferenceResponse> getPreferenceByMember_No(@RequestParam("member_No") int member_No) {
    try {
      PreferenceResponse response = memberCategoryService.getPreferenceByMember_No(member_No);
      return new ApiResponse<>("success", "조회 성공", response);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }
}
