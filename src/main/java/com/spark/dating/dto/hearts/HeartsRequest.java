package com.spark.dating.dto.hearts;

import com.spark.dating.hearts.HeartsStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class HeartsRequest {
	private Long heartsNo;
	private Long matchingNo;
	private HeartsStatus status;
}
