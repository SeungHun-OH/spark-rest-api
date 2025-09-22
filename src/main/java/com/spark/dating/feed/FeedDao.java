package com.spark.dating.feed;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.feed.Feed;

@Mapper
public interface FeedDao {
    public int create(Feed feed);
    public int update(Feed feed);
    public int delete(int f_no);
    public int countAll();
}
