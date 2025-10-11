package com.spark.dating.thread.ai;

import java.util.ArrayList;
import java.util.HashMap;
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

    String context = threadBoards.stream()
        .map(p -> p.getTbTitle() + " " + p.getTbContent())
        .collect(Collectors.joining("\n\n"));

    // 프롬프트용 제목 리스트들
    List<String> titles = newboards.stream()
        .map(b -> b.get("tbTitle"))
        .filter(Objects::nonNull)
        .filter(t -> !t.isBlank())
        .toList();

    String titleList = titles.stream()
        .map(t -> "• " + t)
        .collect(Collectors.joining("\n"));

    String prompt = """
                질문: %s

        너는 지금 연애 고민 게시판의 따뜻한 조언자야.

        아래는 참고용 게시글 데이터야.
        각 게시글에는 "tbTitle"(제목) 과 "tbContent"(본문)이 포함되어 있어.
        이 데이터들을 참고해서 질문자의 고민에 대한 진심 어린 조언을 작성해줘.

        [참고 게시글 요약 목록]
        %s
        [참고 게시글 전체 내용]
        %s
        ---

        작성 원칙:
        1. 반드시 질문자의 고민에 집중해서 답변해. (참고글 복붙 금지)
        2. 하나의 완성된 상담글처럼 자연스럽게 작성해.
        3. 상담자의 말투로, 따뜻하고 진심 어린 어조를 유지해.
        4. 출력은 오직 한글 문장만 포함해야 한다.
           - 영어, 숫자, 이모지, 특수문자, 마크다운 기호(`*`, `#`, `>`, ```, 등) 절대 사용 금지.
           - 한글 외 문자가 생성되면 반드시 제거하고 한글만 남겨야 한다.
        5. 문장은 5~10문장 내외로 구성해.
        6. 문체는 자연스럽고 대화하듯이 써.
        7. ‘첫째’, ‘둘째’ 같은 나열형 문체는 절대 쓰지 마.

        ---

        추가 지시:
        - 답변 마지막에는 짧게 공감 한마디를 덧붙여줘.
          예: "비슷한 고민을 가진 분들도 많아요, 혼자라 생각하지 마요."
        - 그리고 마지막 1~2문장 안에는 아래 [참고 게시글 요약 목록]의 **tbTitle** 값 중에서
          2~3개를 선택해 자연스럽게 언급해.
          - 반드시 실제 존재하는 제목(tbTitle)만 사용해야 해.
          - 새로운 제목을 창작하거나 바꾸지 마.
          - 제목은 따옴표(“ ”)로 감싸서 써야 해.
          - 제목은 너무 많지 않게, 2~3개 정도만 자연스럽게 포함해.

        ---

        출력 형식:
        한글 문장만. (한글 외 문자 전부 금지)

        지금부터 %s 에 대한 따뜻하고 현실적인 상담 답변을 작성해줘.
                        """.formatted(question, titleList, context, question);
    map.put("keywords", keywords);
    map.put("answer", aiOllamaClient.generateText(prompt));
    map.put("boards", newboards);

    // return ("AI뽑아낸 kewords = " + kewords + "\n" + "게시판 리스트AI 쿼리 = \n" + prompt +
    // "\n AI답변 \n----------\n " + aiOllamaClient.generateText(prompt));

    // - 예:
    //   “연락이 줄어드는 게 불안해요”, “그 사람의 마음이 멀어진 걸까” 같은 글에서도 비슷한 고민이 있었어요.
    //   당신도 그 글들처럼 천천히 마음을 다듬어가면 좋겠어요.

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