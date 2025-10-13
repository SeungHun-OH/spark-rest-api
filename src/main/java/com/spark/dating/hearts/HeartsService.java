package com.spark.dating.hearts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.hearts.Hearts;
import com.spark.dating.dto.hearts.HeartsRequest;
import com.spark.dating.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class HeartsService {

    private final MemberService memberService;

    @Autowired
    private HeartsDao heartsDao;

    HeartsService(MemberService memberService) {
        this.memberService = memberService;
    }

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
    
    
    public void acceptHeartRequest(HeartsRequest request) {
    	request.setStatus(HeartsStatus.ACCEPT);
    	heartsDao.updateHeartStatus(request);
    	heartsDao.createMatching(request);
    }
    
    public Long sendHeart(int senderNo, int partnerNo, char requestChannel) {
        // Long receiverNo = memberService.getMemberNoByUuid(partnerUuid);
        Map<String, Object> params = new HashMap<>();
        params.put("senderNo", senderNo);
        params.put("partnerNo", partnerNo);
        params.put("requestChannel", requestChannel);

        heartsDao.insertHeart(params);

        Long hNo = (Long) params.get("hNo");
        return hNo;
    }

    public List<Hearts> getHearts(int memberNo) {
        return heartsDao.selectReceivedHeartRequests(memberNo);
    }

    public void rejectHeartRequest(Long heartsNo) {
    	HeartsRequest request = new HeartsRequest();
    	request.setHeartsNo(heartsNo);
    	request.setStatus(HeartsStatus.REJECT);
    	heartsDao.updateHeartStatus(request);
        heartsDao.rejectHeartRequest(heartsNo);
    }

    public Long isExistdHearts(int senderNo, int partnerNo, char requestChannel) {
        Map<String, Object> params = new HashMap<>();
        params.put("senderNo", senderNo);
        params.put("partnerNo", partnerNo);
        params.put("requestChannel", requestChannel);

        return heartsDao.isExist(params);
    }
}
