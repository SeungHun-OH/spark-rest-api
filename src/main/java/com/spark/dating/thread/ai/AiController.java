package com.spark.dating.thread.ai;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.thread.ThreadBoard;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin("*")
public class AiController {

  @Autowired
  private AiService aiService;

  @GetMapping("/Ai/BoardGenerate/")
  public ThreadBoard AiGenerateBoard() {
    return aiService.generatedRandomPost();
  }

  @GetMapping("/Ai/BoardGenerate/List")
  public void AiGenerateBoard(@RequestParam("count") int count) {
    for(int i = 0; i < count; i++){
      aiService.generatedRandomPost();
    }
  }

  @GetMapping("/Ai/FilterPromtKeword")
  public List<String> FilterPromtKeword(@RequestParam("question") String question) {
    log.info("AiService 컨트롤러 FilterPromtKeword 실행 " + question);
    return aiService.FilterPromtKeword(question);
  }

  @GetMapping("/Ai/searchThreadBoardPrompt")
  public List<ThreadBoard> searchThreadBoardPrompt(@RequestParam("question") String question) {
    log.info("AiService searchThreadBoardPrompt 컨트롤러 실행 추출된 검색 리스트는?" + question);
    return aiService.searchThreadBoardPrompt(question);
  }

  @GetMapping("/Ai/generaeAnswerBoardQuestion")
  public String generateAnswerBoardQuestion(@RequestParam("question") String question) {

    String result = aiService.generateAnswerBoardQuestion(question);
    log.info("AiService generaeAnswerBoardQuestion 컨트롤러 실행 머라왔냐" +  result);
    return result;
  }
}
