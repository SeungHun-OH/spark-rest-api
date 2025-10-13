package com.spark.dating.dto.matching;

import com.spark.dating.hearts.HeartsStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class MatchingLike {

	private Long heartNo;
	private int sendMemberNo;
	private Long receiveMemberNo;
	private char requestChannel;
	private HeartsStatus status;
}
