package com.spark.dating.feed;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public void createFeed(Feed feed, MultipartFile[] files) throws IOException {
        int m_no = AuthenticationContextHolder.getContextMemberNo();
        Feed dbFeed = new Feed();
        dbFeed.setfMemberNo(m_no);
        dbFeed.setfContent(feed.getfContent());
        int rows = feedDao.create(dbFeed);

        if (files != null) {
            for (int i=0; i<files.length; i++) {
                FeedPicture feedPicture = FeedPicture.insertFeedPictures(dbFeed.getfNo(), files[i]);
                feedPictureDao.create(feedPicture);
            }
        }
    }

    public Map<String, Object> updateFeed(Feed feed, MultipartFile[] files) throws IOException {
        Map<String, Object> map = new HashMap<>();
        int feedPicRows = 0;

        if (files != null) {
            for (int i=0; i<files.length; i++) {
                FeedPicture feedPicture = FeedPicture.insertFeedPictures(feed.getfNo(), files[i]);
                feedPicRows += feedPictureDao.create(feedPicture);
            }
        }
        int feedRows = feedDao.update(feed);
        map.put("추가된 feedPictureRows", feedPicRows);
        map.put("수정된 feedRows", feedRows);
        return map;
    }

    public int deleteFeed(int f_no) {
        feedPictureDao.delete(f_no);
        int rows = feedDao.delete(f_no);
        return rows;
    }

    public Map<String, Object> getFeed(int f_no) {
        Feed feed = feedDao.selectByFno(f_no);
        log.info("feed : {}", feed);
        List<FeedPicture> list = feedPictureDao.selectByFno(f_no);

        Map<String, Object> map = new HashMap<>();
        map.put("feed", feed);

        return map;
    }

    public List<Feed> getListByPage(int m_no, Pager pager) {
        List<Feed> feedList = feedDao.selectByPage(m_no, pager);
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

