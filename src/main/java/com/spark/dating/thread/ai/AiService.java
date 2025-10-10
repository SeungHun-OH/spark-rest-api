package com.spark.dating.thread.ai;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.thread.response.ThreadBoardResponse;
import com.spark.dating.member.MemberDao;
import com.spark.dating.thread.ThreadDao;

@Service
public class AiService {

  @Autowired
  private AiOllamaClient aiOllamaClient;

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private ThreadDao threadDao;

   private static final String[] PROMPTS = {
        "연애 고민글을 하나 작성해줘. 20대 여성이 친구에게 털어놓는 듯한 톤으로.",
        "이상형에 대한 고민글을 짧게 작성해줘. 따뜻하지만 현실적인 느낌으로.",
        "짝사랑에 대한 글을 작성해줘. 감정이 섬세하게 담기게.",
        "연락이 줄어드는 사람에 대한 고민글을 작성해줘. 진심 어린 어조로."
    };

    // public ThreadBoardResponse generatedRandomPost() {
      
    //   String prompt = PROMPTS[new Random().nextInt(PROMPTS.length)];
    //   String aiResponse = aiOllamaClient.generateText(prompt);
    //   String[] split = aiOllamaClient.generateText(prompt).split("\")
    // }

}
