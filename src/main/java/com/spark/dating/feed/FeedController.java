package com.spark.dating.feed;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.Pager;
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
     * - 게시물 수정 (Feed) (picture 테이블에서 따로 할 수 없음, 무조건 feed에서 해야 함) -> feedPicture 연결
     * - 게시물 삭제 (f_id) -> feed연결
     * - 개인 피드의 게시물 나열 (m_id, f_id)
     */

    @Autowired
    private FeedService feedService;

    @PostMapping("/")
    public Map<String, Object> createFeed(@RequestPart("feed") Feed feed,
            @RequestPart("files") MultipartFile[] files) throws IOException {
        feedService.createFeed(feed, files);

        Map<String, Object> map = new HashMap<>();
        map = feedService.getFeed(feed.getF_no());
        return map;
    }

    @GetMapping("/")
    public Map<String, Object> getFeed(@RequestParam("f_no") int f_no) {
        Map<String, Object> map = feedService.getFeed(f_no);

        return map;
    }

    @GetMapping("/list")
    public List<Feed> getFeedList(@RequestParam("m_no") int m_no,
            @RequestParam(value = "page_no", defaultValue = "1") int page_no) {
        int totalRows = feedService.totalRows(m_no);
        Pager pager = new Pager(10, 10, totalRows, page_no);
        List<Feed> feedList = feedService.getListByPage(m_no, pager);

        return feedList;
    }

    @PutMapping("/")
    public Map<String, Object> updateFeed(@RequestPart("feed") Feed feed,
            @RequestPart("files") MultipartFile[] files) throws IOException {
        Map<String, Object> map = feedService.updateFeed(feed, files);
        return map;
    }

    @DeleteMapping("/")
    public int deleteFeed(@RequestParam("f_no") int f_no) {
        int rows = feedService.deleteFeed(f_no);
        return rows;
    }
}
