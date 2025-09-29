package com.spark.dating.dto.member;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Member {
    private int mNo;
    private String mId;
    private String mPassword;
    private String mName;
    private String mSsn;
    private int mAge;
    private String mEmail;
    private String mGender;
    private String mPhone;
    private String mNickname;
    private String mRegion;
    private String mBio;
    private String mMbti;
    private String mActive = "T";
    // @JsonProperty("mNo")
    // private int mNo;

    // @JsonProperty("mId")
    // private String mId;

    // @JsonProperty("mPassword")
    // private String mPassword;

    // @JsonProperty("mName")
    // private String mName;

    // @JsonProperty("mSsn")
    // private String mSsn;

    // @JsonProperty("mAge")
    // private int mAge;

    // @JsonProperty("mEmail")
    // private String mEmail;

    // @JsonProperty("mGender")
    // private String mGender;

    // @JsonProperty("mPhone")
    // private String mPhone;

    // @JsonProperty("mNickname")
    // private String mNickname;

    // @JsonProperty("mRegion")
    // private String mRegion;

    // @JsonProperty("mBio")
    // private String mBio;

    // @JsonProperty("mMbti")
    // private String mMbti;

    // @JsonProperty("mActive")
    // private String mActive = "T";
}
