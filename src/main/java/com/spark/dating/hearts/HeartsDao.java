package com.spark.dating.hearts;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.hearts.Hearts;

@Mapper
public interface HeartsDao {
    public int create(Hearts hearts); //h_receiveuser, h_requestchanel
    public List<Hearts> selectByMno(int m_no); //m_no, h_requestchanel
    public int delete(int h_no); //h_no
}