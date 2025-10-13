package com.spark.dating.hearts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.hearts.Hearts;
import com.spark.dating.dto.hearts.HeartsRequest;

@Mapper
public interface HeartsDao {
    void createMatching(HeartsRequest request); //h_receiveuser, h_requestchanel
    void insertHeart(Map<String, Object> params);
    Hearts selectByHno(int h_no);
    List<Hearts> selectByChanel(Map<String, Object> params); //m_no, h_requestchanel
    void rejectHeartRequest(Long heartsNo); //h_no
    List<Hearts> selectReceivedHeartRequests(int memberNo);
    void updateHeartStatus(HeartsRequest request);
    boolean isExist(Map<String, Object> params);
}