package com.spark.dating.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class MemberCategory {

  @Autowired
  MemberCategoryService preferenceService;
    
}
