package com.spark.dating.dto.member.response;

import lombok.Data;

@Data
public class PreferenceCategory {

  private int    pc_no;
  private String pc_type;
  private int    pc_typenum;
  private String pc_group;
  private String pc_name;
  private String pc_desc;

}
