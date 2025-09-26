package com.spark.dating.dto.member.response;

import lombok.Data;

@Data
public class PreferenceCategory {

  private int pcNo;
  private String pcType;
  private int pcTypeNum;
  private String pcGroup;
  private String pcName;
  private String pcDesc;

}
