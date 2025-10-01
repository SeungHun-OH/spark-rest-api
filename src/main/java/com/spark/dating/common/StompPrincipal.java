package com.spark.dating.common;

import java.security.Principal;


public class StompPrincipal implements Principal {

	private final String memberNo;
	
	public StompPrincipal(String memberNo) {
        this.memberNo = memberNo;
    }

	
	@Override
	public String getName() {
		return memberNo+"";
	}

}
