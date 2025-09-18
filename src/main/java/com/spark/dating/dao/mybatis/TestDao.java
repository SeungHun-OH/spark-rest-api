package com.spark.dating.dao.mybatis;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.Test;

@Mapper
public interface TestDao {

	public Test testselect();
}
