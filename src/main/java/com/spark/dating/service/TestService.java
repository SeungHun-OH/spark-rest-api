package com.spark.dating.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dao.mybatis.TestDao;
import com.spark.dating.dto.Test;

@Service
public class TestService {

	@Autowired
	private TestDao testDao;
	
	public Test testselect() {
		return testDao.testselect();
	}
}
