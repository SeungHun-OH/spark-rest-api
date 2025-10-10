package com.spark.dating.dto.matching;

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
	char requestChannel;
}
