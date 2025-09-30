package com.spark.dating.hearts;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        - 좋아요 버튼을 누르면 관심 테이블 생성
        - 관심 테이블 삭제
        - 내가 좋아요한 프로필 리스트
    */

    @Autowired
    private HeartsService heartsService;

    public List<Hearts> createHearts(@RequestParam("m_no") int m_no) {

        return heartsService.insertHearts(m_no);
    }
}
