package com.spark.dating.chat.dao;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StompDao {

	public int isMemberExist(String memberId);
	
}
