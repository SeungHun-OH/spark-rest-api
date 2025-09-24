package com.spark.dating.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberCategoryService {

  @Autowired
  MemberCategoryDao memberCategoryDao;

}
