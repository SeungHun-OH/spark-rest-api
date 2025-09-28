package com.spark.dating.dto.member.response;

import java.util.List;

import lombok.Data;

@Data
public class PreferenceResponse {
  private int memberNo;
  private char memberWho;
  private List<PreferenceCategory> selfPrefers;
  private List<PreferenceCategory> partnerPrefers;
}
