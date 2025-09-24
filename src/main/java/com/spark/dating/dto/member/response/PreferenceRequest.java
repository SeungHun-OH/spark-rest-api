package com.spark.dating.dto.member.response;

import java.util.List;

import lombok.Data;

@Data
public class PreferenceRequest {
  
  private int member_No;
  private List<Integer> preferNos;
}
