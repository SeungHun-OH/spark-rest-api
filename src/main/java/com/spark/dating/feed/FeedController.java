package com.spark.dating.feed;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.feed.Feed;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/feed")
@Slf4j
public class FeedController {
    /*
     * 1. 메인피드
     * - 타인의 피드 무작위로 선정
     * 2. 개인피드
     * - 개인 프로필 (m_id)
     * - 게시물 작성 (Feed)
     * - 게시물 수정 (Feed)
     * - 게시물 삭제 (f_id)
     * - 개인 피드의 게시물 나열 (m_id, f_id)
     */

    @Autowired
    private FeedService feedService;

    @PostMapping("/")
    public Feed createFeed(@RequestBody Feed feed){
        log.info(feed.toString());
        
        return "";
    }

}
