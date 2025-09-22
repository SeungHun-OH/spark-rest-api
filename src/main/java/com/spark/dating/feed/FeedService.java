package com.spark.dating.feed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.feed.Feed;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FeedService {
    @Autowired
    private FeedDao feedDao;

    public void feedCreate(Feed feed) {
        feedDao.create(feed);
    }

    public int feedUpdate(Feed feed) {
        int rows = feedDao.update(feed);
        return rows;
    }

    public int feedDelete(int f_no) {
        int rows = feedDao.delete(f_no);
        return rows;
    }

    public int feedCountAll() {
        int rows = feedDao.countAll();
        return rows;
    }

}
