package com.spark.dating.feed;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import com.spark.dating.dto.feed.Feed;

@Mapper
public interface FeedDao {
    public int create(Feed feed);
    public int update(Feed feed);
    public int delete(int f_no);
    public Feed selectFeedByFno(int f_no); //각 피드
    public List<Feed> selectAllByMno(int m_no); //회원 피드

    public int countAll(int m_no);
}
