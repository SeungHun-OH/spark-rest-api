package com.spark.dating.thread.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spark.dating.dto.member.Member;
import com.spark.dating.dto.thread.BoardReply;
import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.member.MemberDao;
import com.spark.dating.thread.BoardReplyDao;
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

  @Autowired
  private BoardReplyDao boardReplyDao;

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
  public Map<String, Object> generateAnswerBoardQuestion(String question) {
    Map<String, Object> map = new HashMap<>();
    List<String> keywords = FilterPromtKeword(question);

    List<ThreadBoard> threadBoards = threadDao.searchThreadBoardPrompt(keywords);

    List<Map<String, String>> newboards = threadBoards.stream().map(p -> {
      Map<String, String> m = new HashMap<>();
      m.put("tbTitle", p.getTbTitle());
      m.put("tbContent", p.getTbContent());
      return m;
    }).toList();

    List<Integer> indices = new ArrayList<>();
    for (int i = 0; i < threadBoards.size(); i++) {
      indices.add(i);
    }
    Collections.shuffle(indices);

    String title = "";
    for (int i = 0; i < Math.min(threadBoards.size(), 3); i++) {
      title += threadBoards.get(indices.get(i)).getTbTitle() + "\n";
    }
    String content = "";
    for (int i = 0; i < Math.min(threadBoards.size(), 3); i++) {
      content += threadBoards.get(indices.get(i)).getTbContent() + "\n";
    }
    log.info("AiService title 뽑아내기 " + title);
    String prompt = """
        너는 연애 고민 게시판의 따뜻한 상담자야.
        [질문]
        %s
        [참고글 제목]
        %s
        답변은 두 문단으로 써줘.
        1. 첫 문단은 질문자에게 직접 공감하고 따뜻하게 조언하기.
        2. 두 번째 문단은 위 제목들 중 2~3개를 자연스럽게 언급하며,
           비슷한 사례와 조언으로 마무리하기.

        모든 문장은 자연스러운 한글 대화체로 작성하고,
        영어, 숫자, 기호, 이모지는 절대 쓰지 마.
        마지막 한 문장은 짧게 공감으로 마무리해.
        """.formatted(question, title);
    map.put("keywords", keywords);
    map.put("answer", aiOllamaClient.generateText(prompt));
    map.put("boards", newboards);

    return map;
  }

  public List<String> FilterPromtKeword(String question) {
    String prompt = """
              다음 문장에서 핵심 키워드 3~5개를 추출해.
        명사 중심으로만 선택하고, 조사는 빼.
        출력은 반드시 아래 형식으로만 해:
        ["키워드1", "키워드2", "키워드3"]
        그 외 어떤 설명, 문장, JSON 코드 블록, 마크다운 기호도 절대 포함하지 마.
        영어 단어, 숫자, 특수문자는 사용하지 마.
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

  public String aiBoardReplyGenerate(int count) {
    String brNosSt = "";
    List<Integer> brNos = threadDao.getThreadBoardListTbNo();

    for (int i = 0; i < count; i++) {
      int randomBrNum = brNos.get(new Random().nextInt(brNos.size()));
      Member member = memberDao.getRandomMember();

      String tbTitle = threadDao.getThreadBoard(randomBrNum).getTbTitle();
      String tbContent = threadDao.getThreadBoard(randomBrNum).getTbContent();

      String prompt = """
          너는 한국 커뮤니티의 댓글 작성자야.
          항상 한국어로만 대답해야 해. 절대로 영어를 사용하지 마.
          %s 글을 읽고, 글 분위기에 어울리는 자연스러운 짧은 댓글을 한 줄 써줘.

          조건:
          - 반드시 한글로만 작성.
          - 길이 60자 이하.
          - 너무 딱딱하지 않고 자연스럽게 반응해줘.
          """.formatted(tbContent);
      String response = aiOllamaClient.generateText(prompt);

      BoardReply boardReply = new BoardReply();
      boardReply.setBrMemberNo(member.getMNo());
      boardReply.setBrThreadBoardNo(randomBrNum);
      boardReply.setBrContent(response);

      brNosSt += "- 참고게시글 -" + tbTitle + " -생성댓글 - " + boardReply.getBrContent() + "\n\n";

      boardReplyDao.insertBoardReply(boardReply);
    }

    return brNosSt;
  }

  //Ai 연애 성향 분석기
  public String  analyzeLovePersonality(String question) {
    String prompt = """
        너는 연애 심리 분석 전문가야.
        %s 이것은 사용자의 지난 답변이야.
        이 답변의 감정, 태도, 가치관을 고려해서
        다음으로 물어볼 "심층적인 질문" 한 문장 한글로 만들어줘.

        조건:
        - 반드시 한글로 한 문장.
        - 영어나 다른 외국어로 답변 금지.
        - 연애와 성향에 관련된 주제일 것.
        - 너무 일반적인 질문은 금지.

        출력 형식:
        {"question": "다음 질문 문장"},
        """.formatted(question);
        String answer = aiOllamaClient.generateText(prompt);
        log.info("AiService 분석 답변" + answer);
        
    return answer;
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

// 프롬프트용 제목 리스트들
// List<String> titles = newboards.stream()
// .map(b -> b.get("tbTitle"))
// .filter(Objects::nonNull)
// .filter(t -> !t.isBlank())
// .toList();

// String titleList = titles.stream()
// .map(t -> "• " + t)
// .collect(Collectors.joining("\n"));

// String context = threadBoards.stream()
// .map(p -> p.getTbTitle() + " " + p.getTbContent())
// .collect(Collectors.joining("\n\n"));