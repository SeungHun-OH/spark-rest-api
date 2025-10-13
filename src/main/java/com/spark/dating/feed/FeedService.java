package com.spark.dating.feed;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.Pager;
import com.spark.dating.dto.feed.Feed;
import com.spark.dating.dto.feed.FeedPicture;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeedService {
    @Autowired
    private FeedDao feedDao;
    @Autowired
    private FeedPictureDao feedPictureDao;

    //사용자별로 이미 본 피드 번호(f_no)들을 저장하는 Map(세션 단위 중복 방지)
    private final Map<Integer, Set<Integer>> viewedFeedIds = new HashMap<>();

    // ---------------------- CREATE ----------------------
    public void createFeed(Feed feed, MultipartFile[] files) throws IOException {
        int m_no = AuthenticationContextHolder.getContextMemberNo();
        Feed dbFeed = new Feed();
        dbFeed.setfMemberNo(m_no);
        dbFeed.setfContent(feed.getfContent());
        int rows = feedDao.create(dbFeed);

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                FeedPicture feedPicture = FeedPicture.insertFeedPictures(dbFeed.getfNo(), files[i]);
                feedPictureDao.create(feedPicture);
            }
        }
    }

    // ---------------------- UPDATE ----------------------
    public Map<String, Object> updateFeed(Feed feed, MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        int feedPicRows = 0;

        // null 체크 + length 체크 둘 다 하기
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                FeedPicture feedPicture = FeedPicture.insertFeedPictures(feed.getfNo(), files[i]);
                feedPicRows += feedPictureDao.create(feedPicture);
            }
        }

        int feedRows = feedDao.update(feed);
        map.put("추가된 feedPictureRows", feedPicRows);
        map.put("수정된 feedRows", feedRows);
        return map;
    }

    // ---------------------- DELETE ----------------------    
    public int deleteFeed(int f_no) {
        feedPictureDao.delete(f_no);
        int rows = feedDao.delete(f_no);
        return rows;
    }

    // ---------------------- READ (단일) ----------------------
    public Map<String, Object> getFeed(int f_no) {
        Feed feed = feedDao.selectByFno(f_no);
        List<FeedPicture> list = feedPictureDao.selectByFno(f_no);

        Map<String, Object> map = new HashMap<>();
        map.put("feed", feed);

        return map;
    }

    // ---------------------- READ (내 피드 목록) ----------------------
    public List<Feed> getListByPage(int m_no, Pager pager) {
        List<Feed> feedList = feedDao.selectByPage(m_no, pager);
        return feedList;
    }

    // ---------------------- READ (랜덤 피드, 나 제외, 중복 없는 버전) ----------------------
    public List<Feed> getFeedListExceptMe(int m_no, Pager pager, boolean reset) {
        if (reset) {
        // 새로고침 시 기존 기록 제거
        viewedFeedIds.remove(m_no);
    }
        List<Feed> randomFeeds = feedDao.selectRandomFeedExceptMe(m_no);

        //사용자별 이미 본 피드 Set 가져오기 (없으면 생성)
        Set<Integer> seen = viewedFeedIds.computeIfAbsent(m_no, k->new HashSet<>());

        //중복 제외 + 페이지 크기만큼 반환
        List<Feed> feedList = randomFeeds.stream()
            .filter(f->!seen.contains(f.getfNo()))
            .limit(pager.getRowsPerPage())
            .toList();

        feedList.forEach(f -> seen.add(f.getfNo()));
        return feedList;
    }

    public List<Feed> getFeedListByMno(int m_no) {
        List<Feed> feedList = feedDao.selectAllByMno(m_no);
        return feedList;
    }

    public int totalRows(int m_no) {
        int totalRows = feedDao.countAll(m_no);
        return totalRows;
    }
}
