package com.spark.dating.hearts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.hearts.Hearts;
import com.spark.dating.dto.hearts.HeartsRequest;

@Mapper
public interface HeartsDao {
    public void createMatching(HeartsRequest heartsNo); //h_receiveuser, h_requestchanel
    public Hearts selectByHno(int h_no);
    public List<Hearts> selectByChanel(Map<String, Object> params); //m_no, h_requestchanel
    public void rejectHeartRequest(Long heartsNo); //h_no
    
    public List<Hearts> selectReceivedHeartRequests(int memberNo);
}