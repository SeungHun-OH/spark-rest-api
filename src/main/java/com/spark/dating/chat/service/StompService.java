package com.spark.dating.chat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.chat.dao.StompDao;

@Service
public class StompService {

	@Autowired
	private StompDao stompDao;
	
	public int isMemberExist(String memberId) {
		
		System.out.println("==========");
		return stompDao.isMemberExist(memberId);
	}
}
