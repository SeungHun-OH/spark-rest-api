package com.spark.dating.dto.member;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, 
                getterVisibility = JsonAutoDetect.Visibility.NONE, 
                isGetterVisibility = JsonAutoDetect.Visibility.NONE, 
                setterVisibility = JsonAutoDetect.Visibility.NONE)

@Data
public class Member {
    
    @JsonProperty("mNo")
    private int mNo;
    @JsonProperty("mId")
    private String mId;
    @JsonProperty("mPassword")
    private String mPassword;
    @JsonProperty("mName")
    private String mName;
    @JsonProperty("mSsn")
    private String mSsn;
    @JsonProperty("mAge")
    private int mAge;
    @JsonProperty("mEmail")
    private String mEmail;
    @JsonProperty("mGender")
    private String mGender;
    @JsonProperty("mPhone")
    private String mPhone;
    @JsonProperty("mNickname")
    private String mNickname;
    @JsonProperty("mRegion")
    private String mRegion;
    @JsonProperty("mBio")
    private String mBio;
    @JsonProperty("mMbti")
    private String mMbti;
    @JsonProperty("mActive")
    private String mActive = "T";
    @JsonIgnore
    private String mUuid = UUID.randomUUID().toString();
}
