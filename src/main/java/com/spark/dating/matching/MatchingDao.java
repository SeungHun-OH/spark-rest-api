package com.spark.dating.matching;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.matching.Matching;
import com.spark.dating.dto.matching.MatchingLike;
import com.spark.dating.dto.matching.MatchingPicture;

@Mapper
public interface MatchingDao {
    public int create(Matching matching);
    public Matching selectByMtno(int mt_no);
    public int delete(int mt_no);
    public List<Matching>getRandomMatchList(Map<String, Object> myInfo);
    public MatchingPicture getMemberPicture(Long mpNo);
    public void insertHeart(MatchingLike matchingLike);
}
