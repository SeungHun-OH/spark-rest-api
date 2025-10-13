package com.spark.dating.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.spark.dating.dto.Pager;
import com.spark.dating.dto.feed.FeedPicture;

@Mapper
public interface FeedPictureDao {
    public int create(FeedPicture feedPicture);
    public List<FeedPicture> selectByFno(int f_no);
    public FeedPicture selectImgByFpno(int fp_no);
    public List<FeedPicture> selectFirstImg(@Param("m_no")int m_no, @Param("pager")Pager pager);
    // public int update(FeedPicture feedPicture);
    public int delete(int m_no);
    public int deleteFeedPicture(int fp_no);
    public int countAll(int m_no);
}
