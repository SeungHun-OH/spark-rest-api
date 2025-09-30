package com.spark.dating.feed;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.feed.FeedPicture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FeedPictureService {

    @Autowired
    FeedPictureDao feedPictureDao;

    public List<FeedPicture> getPictures(int f_no) {
        List<FeedPicture> list = new ArrayList<>();
        list = feedPictureDao.selectByFno(f_no);
        return list;
    }

    public FeedPicture getPicture(int fp_no) {
        return feedPictureDao.selectImgByFpno(fp_no);
    }
    
    public List<FeedPicture> getFirstImgOfFeed(int m_no) {
    	return feedPictureDao.selectFirstImg(m_no);
    }

}
