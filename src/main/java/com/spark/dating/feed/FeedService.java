package com.spark.dating.feed;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    public void createFeed(Feed feed, MultipartFile[] files) throws IOException{
        feedDao.create(feed);

        for (int i=0; i<files.length; i++) {

            FeedPicture feedPicture = new FeedPicture();
            feedPicture.setFp_feedno(feed.getF_no());
            feedPicture.setFp_no(i);
            feedPicture.setFp_attachoname(files[i].getOriginalFilename());
            feedPicture.setFp_attachdata(files[i].getBytes());
            feedPicture.setFp_attachtype(files[i].getContentType());

            feedPictureDao.create(feedPicture);
        }
    }

    public int updateFeed(Feed feed) {
        int rows = feedDao.update(feed);
        return rows;
    }

    public int deleteFeed(int f_no) {
        int rows = feedDao.delete(f_no);
        return rows;
    }

    public Feed getFeed(int f_no) {
        Feed feed = feedDao.selectFeedByFno(f_no);

        return feed;
    }

    public List<Feed> getFeedListByMno(int m_no) {
        List<Feed> feedList = feedDao.selectAllByMno(m_no);

        int count = feedList.size();
        return feedList;
    }

}
