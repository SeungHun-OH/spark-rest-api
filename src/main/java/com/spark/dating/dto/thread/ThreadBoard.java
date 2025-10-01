package com.spark.dating.dto.thread;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Data;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY, 
                getterVisibility = JsonAutoDetect.Visibility.NONE, 
                isGetterVisibility = JsonAutoDetect.Visibility.NONE, 
                setterVisibility = JsonAutoDetect.Visibility.NONE)
@Data
public class ThreadBoard {

  private int tbNo;
  private String tbTitle;
  private String tbContent;
  private Date tbDate;

  private String tbActive;

  private int tbLickCount;
  private int tbMemberNo;
  private int tbViewCount;
  private Integer tbImageNo;

  private Date createdAt;
  private Date updatedAt;
}
