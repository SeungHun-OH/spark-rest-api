package com.spark.dating.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.spark.dating.dto.feed.FeedPicture;

@Mapper
public interface FeedPictureDao {
    public int create(FeedPicture feedPicture);
    public List<FeedPicture> selectByFno(int f_no);
    public FeedPicture selectByFpno(int fp_no);
    // public int update(FeedPicture feedPicture);
    public int delete(int fp_feedno);
    public int countAll(int m_no);
}
