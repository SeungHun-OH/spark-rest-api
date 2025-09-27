package com.spark.dating.dto.member.response;

import java.util.List;

import lombok.Data;

@Data
public class PreferenceRequest {
  
  private int memberNo;
  private String memberWho;
  
  private List<Integer> preferNos;
}
