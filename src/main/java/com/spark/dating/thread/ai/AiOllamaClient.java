package com.spark.dating.thread.ai;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Slf4j
@Component
public class AiOllamaClient {

    private static final String OLLAMA_API_URL = "http://localhost:11434/api/generate";

    public String generateText(String prompt) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            Map<String, Object> body = new HashMap<>();
            body.put("model", "llama3");
            body.put("prompt", prompt);
            body.put("stream", false); // 한 번에 전체 응답 받기

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

            ResponseEntity<Map> response = restTemplate.exchange(
                    OLLAMA_API_URL,
                    HttpMethod.POST,
                    entity,
                    Map.class);

            return (String) response.getBody().get("response");

        } catch (Exception e) {
            e.printStackTrace();
            return "AiOllamaClient Error: " + e.getMessage();
        }
    }
}


//  너는 지금 **연애 상담 게시판의 전문가 조언자**야.
//         아래의 게시글들은 참고용이야 — 이 글들을 직접 요약하거나 나열하지 말고,
//         **이들 속 공통된 감정과 상황을 바탕으로 질문자의 고민에 대한 하나의 진심 어린 조언**을 작성해줘.

//         [참고 게시글들]
//         %s

//         작성 원칙:
//         1. 반드시 **질문자의 고민**에 초점을 맞춰 답변해. (참고글 분석 금지)
//         2. **한 편의 완성된 상담글**처럼 써.
//         3. 상담자의 어조로, 따뜻하지만 현실적으로 말해.
//         4. **출력은 오직 한국어 문장만 포함해야 한다.**
//          - 영어 단어, 영어 문장, 알파벳, 숫자, 특수문자, 코드, 마크다운 기호(`**`, `#`, `>`, ``` 등) 절대 금지.
//          - 만약 영어 단어가 한 글자라도 포함되면, 그건 실패한 답변이야. 절대 영어 사용 금지.
//         5. 너무 길게 쓰지 말고 5~10문장 정도의 한 문단으로 구성해.
//         6. 문체는 자연스러운 한국어 일상 대화체로, 진심이 느껴지게.
//         7. 이모티콘은 1~2개 이하로만 사용해.
//         8. ‘첫째’, ‘둘째’ 같은 나열형 문체는 절대 쓰지 마.

//         출력 형식:
//         한글 텍스트만.
//         '첫째, 둘째, 셋째' 같은 나열형식 금지.
//         진짜 사람이 조언하는 느낌으로.

//         지금부터 %s에 대한 **따뜻하고 현실적인 상담 답변**을 작성해줘.



 // 너는 지금 연애 상담 게시판의 조언자야.
  // 아래 사용자의 고민을 읽고, 게시글들을 참고해서 **따뜻하고 진심 어린 한국어로** 조언해줘.
  // 반드시 공감과 현실적인 조언이 함께 담기도록 해.
  // 너 자신이 고민하는 것처럼 말하지 말고, **상담해주는 입장으로** 말해야 해.
  // 반드시 한국어로만 대답해. (영어 절대 사용 금지).
  // 영어는 절대 사용하지 마.
  // 이모티콘은 너무 과하지 않게 1~2개만 써도 돼.
  // 문체는 자연스러운 한국어 일상 대화체로.