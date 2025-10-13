package com.spark.dating.dto.hearts;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
public class Hearts {
	
	private Long no;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime requestDate;
	
	private char requestChannel;
	private String name;
	private int age;
	private String region;
	private String uuid;
	private Long mpNo;
	
}
