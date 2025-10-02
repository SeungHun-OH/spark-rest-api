package com.spark.dating.hearts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.hearts.Hearts;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hearts")
@Slf4j
public class HeartsController {
    /*
        - 관심 테이블 생성
            - 타인의 피드에서 좋아요를 눌렀을 때 | 매칭 부분에서 좋아요 눌렀을 때
                -> 매칭 부분에 그 사람 안 나옴 + 그 사람 피드 좋아요 비활성화
        - 관심 테이블 삭제
            - 타인의 피드에서 좋아요를 삭제할 때
            - 좋아요 페이지에서 카드를 거절했을 때
        - 내가 좋아요한 프로필 리스트 (추후에 구현)
        - 나를 좋아요한 프로필 리스트
    */
    @Autowired
    private HeartsService heartsService;

    @PostMapping("/")
    public Hearts createHearts(@RequestBody Hearts hearts) {
        return heartsService.createHearts(hearts);
    }

    //나를 좋아요한 리스트 //requestChanel에 따라 변경 가능
    @GetMapping("/")
    public List<Hearts> getHearts(@RequestParam("h_requestchanel") char h_requestchanel) {
        return heartsService.getHearts(h_requestchanel);
    }

    @DeleteMapping("/")
    public int deleteHearts(@RequestParam("h_no") int h_no) {
        return heartsService.deleteHearts(h_no);
    }
}
