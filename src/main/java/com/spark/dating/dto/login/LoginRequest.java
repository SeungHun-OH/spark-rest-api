package com.spark.dating.dto.login;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class LoginRequest {

	@NotBlank(message = "아이디를 입력해주세요.")
	private String memberId;
	
	@NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 5, max = 20, message = "비밀번호는 5~20자 사이여야 합니다.")
	private String memberPwd;

	public void setMemberId(String memberId) {
		this.memberId = memberId.trim();
	}

	public void setMemberPwd(String memberPwd) {
		this.memberPwd = memberPwd.trim();
	}

}
