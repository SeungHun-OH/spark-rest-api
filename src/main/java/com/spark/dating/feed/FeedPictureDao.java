package com.spark.dating.feed;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.multipart.MultipartFile;

import com.spark.dating.dto.feed.FeedPicture;

@Mapper
public interface FeedPictureDao {
    public int create(FeedPicture feedPicture);
    public MultipartFile[] getFeedPicture(int f_no);
    public int update(FeedPicture feedPicture);
    public int delete(int fp_no);
    public int countAll(int m_no);
}
