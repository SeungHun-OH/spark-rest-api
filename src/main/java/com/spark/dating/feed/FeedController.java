package com.spark.dating.feed;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
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
    public Feed createFeed(@RequestPart("feed") Feed feed,
                           @RequestPart("file") MultipartFile[] files) throws IOException{
        feedService.createFeed(feed, files);
        Feed dbFeed = feedService.getFeed(feed.getF_no());
        return dbFeed;
    }

    @GetMapping("/")
    public Feed getFeed(@RequestParam("f_no") int f_no) {
        Feed feed = feedService.getFeed(f_no);
        return feed;
    }

    @GetMapping("/list")
    public List<Feed> getFeedList(@RequestParam("m_no") int m_no) {
        List<Feed> feedList = feedService.getFeedListByMno(m_no);
        return feedList;
    }
    



}
