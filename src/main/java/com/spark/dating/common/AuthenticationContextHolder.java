package com.spark.dating.common;

import com.spark.dating.dto.member.Member;


public class AuthenticationContextHolder {

	private static final ThreadLocal<Member> context = new ThreadLocal<>();
	
	private AuthenticationContextHolder() {};
	
	
	public static void setContext(Member member) {  
        context.set(member);  
    }  
	
	public static Member getContext() {
		return context.get();
	}
	
	public static void clear() {
		context.remove();
	}
	
	public static int getContextMemberNo() {
		return context.get().getMNo();
	}
}
