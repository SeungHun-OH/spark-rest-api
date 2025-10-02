package com.spark.dating.matching;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.matching.Matching;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchingService {
    
    @Autowired
    private MatchingDao matchingDao;

    public void createMatching(int h_no) {
        
        Matching matching = new Matching();
        matching.setMtHeartsno(h_no);

        matchingDao.create(matching); //insert 이후 matching.mtNo에 시퀀스 값 채워짐
        matchingDao.selectByMtno(matching.getMtNo());
    }

    public Matching getMatching(int mt_no) {
        return matchingDao.selectByMtno(mt_no);
    }

    public int deleteMatching(int mt_no) {
        return matchingDao.delete(mt_no);
    }
}
