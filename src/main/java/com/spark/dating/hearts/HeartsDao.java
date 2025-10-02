package com.spark.dating.hearts;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.hearts.Hearts;

@Mapper
public interface HeartsDao {
    public int create(Hearts hearts); //h_receiveuser, h_requestchanel
    public Hearts selectByHno(int h_no);
    public List<Hearts> selectByChanel(Map<String, Object> params); //m_no, h_requestchanel
    public int delete(int h_no); //h_no
}