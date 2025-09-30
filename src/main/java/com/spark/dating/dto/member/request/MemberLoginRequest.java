package com.spark.dating.dto.member.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberLoginRequest {

  @JsonProperty("mId")
  private String mId;
  @JsonProperty("mPassword")
  private String mPassword;
}
