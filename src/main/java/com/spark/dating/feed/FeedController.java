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

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.Pager;
import com.spark.dating.dto.feed.Feed;
import com.spark.dating.dto.member.MemberForFeed;
import com.spark.dating.member.MemberService;

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
    @Autowired
    private MemberService memberService;

    @PostMapping("/")
    public void createFeed(@RequestPart("feed") Feed feed,
            @RequestPart("files") MultipartFile[] files) throws IOException {
        feedService.createFeed(feed, files);
    }

    @GetMapping("/")
    public Map<String, Object> getFeed(@RequestParam("f_no") int f_no) {
        Map<String, Object> map = feedService.getFeed(f_no);

        return map;
    }

    @GetMapping("/list")
    public List<Feed> getFeedList(@RequestParam(value = "m_no", required = false) Integer m_no, @RequestParam(value = "page_no", defaultValue = "1") int page_no) {
        int memberNo;
        //타인
        if (m_no != null) {
            memberNo = m_no;
        //본인
        } else {
            memberNo = AuthenticationContextHolder.getContextMemberNo();
        }
        int totalRows = feedService.totalRows(memberNo);
        Pager pager = new Pager(10, 10, totalRows, page_no);
        List<Feed> feedList = feedService.getListByPage(memberNo, pager);

        return feedList;
    }


    @GetMapping("/main")
    public List<Feed> getFeedListExceptMe(
        @RequestParam("m_no") int m_no, 
        @RequestParam(value = "page_no", defaultValue = "1") int page_no,
        @RequestParam(value = "reset", required = false, defaultValue = "false") boolean reset) {

        int totalRows = feedService.totalRows(m_no);
        //page_no가 1이면 3개, 그 이후엔 1개씩
        Pager pager = new Pager(3, 10, totalRows, page_no);
        return feedService.getFeedListExceptMe(m_no, pager, reset);
    }

    // 타인 피드
    // (pathvariable / requestParam : mid) + 하나 생성
    /*
    @GetMapping("/list")
    public List<Feed> getFeedList(@RequestParam("m_nickname") String m_nickname,
            @RequestParam(value = "page_no", defaultValue = "1") int page_no) {

        MemberForFeed memberForFeed = memberService.selectMemberByMnickname(m_nickname);

        int m_no = memberForFeed.getmNo();
        int totalRows = feedService.totalRows(m_no);

        Pager pager = new Pager(10, 10, totalRows, page_no);
        List<Feed> feedList = feedService.getListByPage(m_no, pager);

        return feedList;
    }
    */

    @PutMapping("/")
    public Map<String, Object> updateFeed(@RequestPart("feed") Feed feed,
            @RequestPart(value = "files", required = false) MultipartFile[] files) throws IOException {
        Map<String, Object> map = feedService.updateFeed(feed, files);
        return map;
    }

    @DeleteMapping("/")
    public int deleteFeed(@RequestParam("f_no") int f_no) {
        int rows = feedService.deleteFeed(f_no);
        return rows;
    }
}
