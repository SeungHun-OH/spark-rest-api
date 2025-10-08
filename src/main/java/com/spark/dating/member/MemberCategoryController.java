package com.spark.dating.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.member.response.PreferenceCategory;
import com.spark.dating.dto.member.response.PreferenceRequest;
import com.spark.dating.dto.member.response.PreferenceResponse;

// 명관님 ------------------------------------------------------------------------------------------

@RestController
public class MemberCategoryController {

  @Autowired
  MemberCategoryService memberCategoryService;

  // 멤버 카테고리 등록
  @PostMapping("/member/categories")
  public ApiResponse<Integer> insertMemberCategories(@RequestBody PreferenceRequest request) {
    try {
      int insertedCount = memberCategoryService.insertMemberCategories(request.getMemberNo(), request.getMemberWho(),
          request.getPreferNos());
      return new ApiResponse<Integer>("success", insertedCount + "개의 카테고리 저장 완료", insertedCount);
    } catch (Exception e) {
      return new ApiResponse<Integer>("fail", e.getMessage(), null);
    }
  }

  // 멤버 카테고리 목록 조회(Static)
  @PostMapping("/member/categories/static")
  public List<PreferenceCategory> getAllCategoryStatic() {
    return memberCategoryService.getAllCategoryStatic();
  }

  // 멤버 선택 카테고리 조회
  @GetMapping("/member/membercategories")
  public ApiResponse<PreferenceResponse> getPreferenceByMember_No(@RequestParam("memberNo") int memberNo) {
    try {
      PreferenceResponse response = memberCategoryService.getPreferenceByMember_No(memberNo);
      return new ApiResponse<>("success", "조회 성공", response);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }

  // 멤버 선택 카테고리 삭제
  @DeleteMapping("/member/membercategories")
  public ApiResponse<Integer> deleteCategoriesByMemberWho(@RequestParam("memberNo") int memberNo,
      @RequestParam("memberWho") String memberWho) {
    try {
      int deleteCount = memberCategoryService.deleteCategoriesByMemberWho(memberNo, memberWho);
      return new ApiResponse<>("success", "멤버 카테고리 삭제성공", deleteCount);
    } catch (Exception e) {
      return new ApiResponse<>("success", "삭제실패" + e.getMessage(), 0);
    }
  }

  // 취미 카테고리 조회
  @GetMapping("/member/membercategories/hobby")
  public ApiResponse<List<PreferenceCategory>> getHobbyCategoriesByMemberNo() {
    try {
      int memberNo = AuthenticationContextHolder.getContextMemberNo();
      List<PreferenceCategory> hobbies = memberCategoryService.getHobbyCategoriesByMemberNo(memberNo);
      return new ApiResponse<>("success", "취미 카테고리 조회 성공", hobbies);
    } catch (Exception e) {
      return new ApiResponse<>("fail", e.getMessage(), null);
    }
  }
}

// 주희님
// ------------------------------------------------------------------------------------------
