package com.spark.dating.matching;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.matching.Matching;

@Mapper
public interface MatchingDao {
    public int create(Matching matching);
    public Matching selectByMtno(int mt_no);
    public int delete(int mt_no);
}
