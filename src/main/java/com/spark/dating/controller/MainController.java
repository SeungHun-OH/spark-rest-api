package com.spark.dating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.Test;
import com.spark.dating.service.TestService;

@RestController
public class MainController {

	@Autowired
	private TestService testService;
	
	@RequestMapping("/test")
	public Test testSelect() {
		return testService.testselect();
	}
}
