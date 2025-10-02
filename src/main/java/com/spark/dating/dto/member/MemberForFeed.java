package com.spark.dating.dto.member;

import lombok.NoArgsConstructor;
import lombok.ToString;

// @AllArgsConstructor
@NoArgsConstructor
@ToString
public class MemberForFeed {
    
    private int mNo;
    private String mId;
    private String mName;
    private int mAge;
    private String mNickname;
    private String mBio;
    private String mMbti;

    public int getmNo() {
        return mNo;
    }
    public String getmId() {
        return mId;
    }
    public String getmName() {
        return mName;
    }
    public int getmAge() {
        return mAge;
    }
    public String getmNickname() {
        return mNickname;
    }
    public String getmBio() {
        return mBio;
    }
    public String getmMbti() {
        return mMbti;
    }


}
