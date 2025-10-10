package com.spark.dating.thread.ai;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.thread.ThreadBoard;

@RestController
@CrossOrigin("*")
public class AiController {

  @Autowired
  private AiService aiService;

  @GetMapping("/Ai/Board/")
  public ThreadBoard AiGenerateBoard(){
    return aiService.generatedRandomPost();
  }

}
