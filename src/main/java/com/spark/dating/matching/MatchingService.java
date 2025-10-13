package com.spark.dating.matching;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.matching.Matching;
import com.spark.dating.dto.matching.MatchingLike;
import com.spark.dating.dto.matching.MatchingPicture;
import com.spark.dating.dto.member.MemberPicture;
import com.spark.dating.hearts.HeartsStatus;
import com.spark.dating.member.MemberService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MatchingService {
    
    @Autowired
    private MatchingDao matchingDao;
    
    @Autowired
    private MemberService memberService;

    public void createMatching(int h_no) {
        
//        Matching matching = new Matching();
//        matching.setMtHeartsno(h_no);
//
//        matchingDao.create(matching); //insert 이후 matching.mtNo에 시퀀스 값 채워짐
//        matchingDao.selectByMtno(matching.getMtNo());
    }

    public List<Matching> getMatching(int memberNo) {
    	String memberGender = memberService.selectMemberGenderByMno(memberNo);
    	Map<String, Object> myInfo = new HashMap<>();
    	myInfo.put("memberNo", memberNo);
    	myInfo.put("memberGender", memberGender);
    	
    	// 쿼리문은 로그인한 사용자, 같은 성별인 사람을 제외하고 랜덤하게 섞어 상위 5개를 조회한 데이터를 반환함
    	return matchingDao.getRandomMatchList(myInfo);
        
    }
    public void matchingPostLike(int sendMemberNo,String partnerUuid) {
    	// 회원을 식별하는 uuid를 가지고 mno를 조회 후 로그인한 회원(요청한 보낸 회원), 상대방(요청을 받는 회원), 채널(매칭 or 피드)를 가진 객체의 값을 insert
    	Long reciveMemberNo = memberService.getMemberNoByUuid(partnerUuid);
    	matchingDao.insertHeart(MatchingLike.builder().sendMemberNo(sendMemberNo).receiveMemberNo(reciveMemberNo).requestChannel('M').status(HeartsStatus.PENDING).build());
    }
    
    public MatchingPicture getMemberPicture(Long mpNo) {
    	return matchingDao.getMemberPicture(mpNo);
    }

    public int deleteMatching(int mt_no) {
        return matchingDao.delete(mt_no);
    }
}
