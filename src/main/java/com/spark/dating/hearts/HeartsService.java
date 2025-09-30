package com.spark.dating.hearts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.hearts.Hearts;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class HeartsService {

    @Autowired
    private HeartsDao heartsDao;

    public List<Hearts> insertHearts(int m_no) {
        int totalRows = heartsDao.create(null);
        log.info("totalRows : {}", totalRows);

        //가지고 온 거 확인하기
        List<Hearts> list = heartsDao.selectByMno(m_no);
        return list;
    }

}
