package com.spark.dating.thread.ai;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.dto.thread.response.ThreadBoardResponse;
import com.spark.dating.member.MemberDao;
import com.spark.dating.thread.ThreadDao;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AiService {

  @Autowired
  private AiOllamaClient aiOllamaClient;

  @Autowired
  private MemberDao memberDao;

  @Autowired
  private ThreadDao threadDao;

  private static final String[] PROMPTS = {
      "연애 고민글을 하나 작성해줘. 20대 여성이 친구에게 털어놓는 듯한 톤으로, 제목과 본문 모두 한국어로 작성해. 영어 사용 금지.",

       "20대 여성이 친구에게 고민을 털어놓듯이, 섬세하고 일기 같은 톤으로 연애 고민글을 작성해줘. "
       + "문장은 자연스럽고 감정의 흐름이 이어지게, 너무 딱딱하거나 설명적이지 않게 써줘. "
       + "문단 간에 숨을 고르듯이 줄바꿈을 넣고, 이모티콘은 적당히 감정선에 맞게 사용해줘. "
       + "반드시 한국어로만 작성하고, 번역투 없이 영어 제목은 절대 넣지 마. "
       + "**형식:**\n"
       + "**제목:** (짧고 감정적인 한 문장)\n"
       + "**본문:** (감정선이 드러나는 5~10문장 정도의 내용)"

      // "이상형에 대한 고민글을 짧게 작성해줘. 따뜻하지만 현실적인 느낌으로 한국어로 작성해.",
      // "짝사랑에 대한 글을 작성해줘. 감정이 섬세하게 담기게 한국어로 작성해.",
      // "연락이 줄어드는 사람에 대한 고민글을 작성해줘. 진심 어린 어조로 한국어로 작성해."
  };

  public ThreadBoard generatedRandomPost() {
      
      String prompt = PROMPTS[new Random().nextInt(PROMPTS.length)];

      String aiResponse = aiOllamaClient.generateText(prompt);

      String[] split = aiResponse.split("\n" ,2);
      String title = split.length > 0 ? split[0].replaceAll("제목[:：]?", "").trim() : "AI 연애글";
      String content = split.length > 1 ? split[1].trim() : aiResponse ;

      var member = memberDao.getRandomMember();

    

      ThreadBoard threadBoard = new ThreadBoard();
      threadBoard.setTbTitle(title);
      threadBoard.setTbContent(content);
      threadBoard.setTbMemberNo(member.getMNo());

      int tbNo = threadDao.insertThreadBoard(threadBoard);
      ThreadBoard threadboardRes = threadDao.getThreadBoard(threadBoard.getTbNo());

      log.info("AI가만든 aiReponse?" + aiResponse);

      log.info("AI가만든 보드는?" + threadBoard.toString());

      log.info("AI가만든 보드는 tbNo는? :" + tbNo);

      log.info("AI가만들고 디비에서 받은 보드는?" + threadboardRes.toString());

      return threadboardRes;
    }

}
