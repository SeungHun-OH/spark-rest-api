package com.spark.dating.hearts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.hearts.Hearts;
import com.spark.dating.dto.hearts.HeartsRequest;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HeartsService {

    @Autowired
    private HeartsDao heartsDao;

//    public Hearts createHearts(Hearts hearts) {
//
//        //이미 테이블이 존재하는지 확인하기 (send, receive, )
//        //좋아요 테이블 자체가 만들어지지 않으면 -> ..? 만들어지기는 해야지
//        int m_no = AuthenticationContextHolder.getContextMemberNo();
////        hearts.sethReceiveUser(m_no);
//        int totalRows = heartsDao.create(hearts);
//        log.info("totalRows : {}", totalRows);
//
//        return heartsDao.selectByHno(m_no);
//    }

//    public List<Hearts> getHearts(char h_requestchanel) {
//        Map<String, Object> params = new HashMap<>();
//        int m_no = AuthenticationContextHolder.getContextMemberNo();
//        params.put("m_no", m_no);
//        params.put("h_requestchanel", h_requestchanel);
//
//        return heartsDao.selectByChanel(params);
//    }
    
    
    public void acceptHeartRequest(HeartsRequest heartsNo) {
    	heartsDao.createMatching(heartsNo);
    }
    
    public List<Hearts> getHearts(int memberNo) {
        return heartsDao.selectReceivedHeartRequests(memberNo);
    }

    public void rejectHeartRequest(Long heartsNo) {
    	System.err.println(heartsNo);
        heartsDao.rejectHeartRequest(heartsNo);
    }
}
