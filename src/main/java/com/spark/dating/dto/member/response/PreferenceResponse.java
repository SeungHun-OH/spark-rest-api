package com.spark.dating.dto.member.response;

import java.util.List;

import lombok.Data;

@Data
public class PreferenceResponse {
  private int memberNo;

  private List<PreferenceCategory> SelfPrefers;
  private List<PreferenceCategory> partnerPrefers;
}
