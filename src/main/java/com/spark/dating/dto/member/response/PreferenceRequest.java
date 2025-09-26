package com.spark.dating.dto.member.response;

import java.util.List;

import lombok.Data;

@Data
public class PreferenceRequest {
  
  private int memberNo;
  private List<Integer> preferNos;
}
