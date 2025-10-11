package com.spark.dating.thread.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.dating.dto.thread.ThreadBoard;
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
      "20~30대 남녀가 친구에게 털어놓듯이, 감정이 섬세하게 느껴지는 짧은 연애 고민글을 작성해줘. "
          + "글 전체 길이는 반드시 50자 이상, 150자 이하로 제한해. "
          + "한글로만 작성하고, 영어 단어나 번역투, 불필요한 설명 절대 금지. "
          + "결과물에는 오직 '제목'과 '본문'만 포함하고, 다른 설명이나 추가 문장은 절대 쓰지 마. "
          + "문장은 일기처럼 자연스럽게 이어지게 하고, 한 문단으로만 써줘. "
          + "공감과 현실적인 감정이 느껴지게 해줘. "
          + "형식 예시는 다음과 같아. 하지만 예시는 출력하지 마.\n"
          + "예시: 제목: ...  본문: ..."
  };

  public ThreadBoard generatedRandomPost() {

    String prompt = PROMPTS[new Random().nextInt(PROMPTS.length)];

    String aiResponse = aiOllamaClient.generateText(prompt);

    String[] split = aiResponse.split("\n", 2);
    String title = split.length > 0 ? split[0].replaceAll("제목[:：]?", "").trim() : "AI 연애글";
    String content = split.length > 1 ? split[1].trim() : aiResponse;
    // 문단 자르기
    content = content.replaceAll("\\n{2,}", " ").replaceAll("\\n", " ").trim();

    var member = memberDao.getRandomMember();

    ThreadBoard threadBoard = new ThreadBoard();
    threadBoard.setTbTitle(title);
    threadBoard.setTbContent(content);
    threadBoard.setTbMemberNo(member.getMNo());

    threadDao.insertThreadBoard(threadBoard);
    ThreadBoard threadboardRes = threadDao.getThreadBoard(threadBoard.getTbNo());
    return threadboardRes;
  }

  // 프롬프트로 단어를 AI로 뽑아 게시판 RAG 기반으로 질문 -> AI -> RAG -> 답변
  public String generateAnswerBoardQuestion(String question) {

    List<String> kewords = FilterPromtKeword(question);

    String context = threadDao.searchThreadBoardPrompt(kewords).stream()
        .map(p -> p.getTbTitle() + " " + p.getTbContent())
        .collect(Collectors.joining("\n\n"));
    String prompt = """
        질문: %s
        너는 지금 연애 상담 게시판의 조언자야.
        아래 사용자의 고민을 읽고, 게시글들을 참고해서 **따뜻하고 진심 어린 한국어로** 조언해줘.
        반드시 공감과 현실적인 조언이 함께 담기도록 해.
        너 자신이 고민하는 것처럼 말하지 말고, **상담해주는 입장으로** 말해야 해.
        영어는 절대 사용하지 마.
        %s
        위 내용을 참고해서 공감 있고 따뜻하게, 사람처럼 대화하듯이 대답해줘.
        """.formatted(question, context);
    // return ("AI뽑아낸 kewords = " + kewords + "\n" + "게시판 리스트AI 쿼리 = \n" + prompt + "\n AI답변 \n----------\n " + aiOllamaClient.generateText(prompt));
    return (aiOllamaClient.generateText(prompt)); 
  }

  public List<String> FilterPromtKeword(String question) {
    String prompt = """
        다음 문장에서 검색에 사용할 핵심 키워드만 3~5개 뽑아줘.
        - 반드시 **JSON 문자열 배열(String Array)** 형식으로만 출력해.
        - 예를 들어 ["연락", "고민", "어려움"] 처럼, 각 단어는 반드시 따옴표("")로 감싸고 쉼표로 구분해.
        - **객체나 키(`word:` 등)**, 설명 문장, 다른 텍스트는 절대 포함하지 마.
        - JSON 배열 외의 문장, 해설, 설명, 마크다운 문법(예: ```json) 도 포함하지 마.
        - 불필요한 조사, 접속사, 형용사는 제외하고 **명사 중심**으로만 선택해.
        - 영어 단어는 절대 포함하지 말고, 반드시 **한글 단어만** 사용해.
        예시:
        ["연락", "고민", "어려움"]
                문장: "%s"
                """.formatted(question);
    try {
      String response = aiOllamaClient.generateText(prompt);
      String jsonPart = response.substring(response.indexOf("["), response.indexOf("]") + 1);
      List<String> kewords = new ObjectMapper().readValue(jsonPart, new TypeReference<>() {
      });

      log.info("response" + response);
      log.info("jsonPart" + jsonPart);
      log.info("kewords" + kewords.toString());

      return kewords;

    } catch (Exception e) {
      log.error("FilterPromptKeyword 실행 중 오류", e);
      return List.of(question);
    }
  }

  public List<ThreadBoard> searchThreadBoardPrompt(String question) {
    return threadDao.searchThreadBoardPrompt(FilterPromtKeword(question));
  }
}

// log.info("AI가만든 aiReponse?" + aiResponse);
// log.info("AI가만든 보드는?" + threadBoard.toString());
// log.info("AI가만들고 디비에서 받은 보드는?" + threadboardRes.toString());

// public List<String> FilterPromtKeword(String question) {
// log.info("AiService FilterPromptedKeword 실행" + question);
// String propt = """
// 다음 문장에서 검색에 사용할 핵심 키워드만 3~5개 뽑아줘.
// 불필요한 조사, 접속사는 빼고, 명사 중심으로.
// 결과는 JSON 배열 형식으로.
// 문장: "%s"
// """.formatted(question);

// try {
// String response = aiOllamaClient.generateText(propt);

// String jsonPart = response.substring(response.indexOf("["),
// response.indexOf("]") +1);
// List<Map<String, String>> keywordObjects = new
// ObjectMapper().readValue(jsonPart, new TypeReference<>() {});

// // List<String>keywords = keywordObjects.stream().map(e ->
// e.get("word")).filter(Objects::nonNull).toList();
// List<String>keywords = keywordObjects.stream().map(e ->
// e.get("word")).toList();

// log.info("response response \n" + response);
// log.info("response JsonPart \n" + jsonPart);
// log.info("response keywordObjects \n" + keywordObjects);
// log.info("response keywords \n" + keywords);

// return keywords;

// } catch (Exception e) {
// log.info("aiOllamaClient.generateText실행오류" + e);
// return List.of(question);
// }
// }