package com.spark.dating.dto.login;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {

	private String JwtToken;
	private String memberUuid;
}
