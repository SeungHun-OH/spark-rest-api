package com.spark.dating.dto.chat;

import java.util.Base64;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class ChatMemberProfile {
	
	
	private String mName;
	
	private int mAge;
	
	@JsonIgnore
    private String mpAttachType;
	
	@JsonInclude(Include.NON_NULL)
    private String mpBase64Data;
    
    public void setMpAttachData(byte[] mpAttachData) {
        if (mpAttachData != null) {
            String base64 = Base64.getEncoder().encodeToString(mpAttachData);
            this.mpBase64Data = "data:" + this.mpAttachType + ";base64," + base64;
        }
    }

    @JsonProperty("name")
	public String getmName() {
		return mName;
	}

	@JsonProperty("age")
	public int getmAge() {
		return mAge;
	}
	
	
    
    
}
