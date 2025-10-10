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
    	
    	return matchingDao.getRandomMatchList(myInfo);
        
    }
    public void matchingPostLike(int sendMemberNo,String partnerUuid) {
    	System.out.println(partnerUuid);
    	Long reciveMemberNo = memberService.getMemberNoByUuid(partnerUuid);
    	matchingDao.insertHeart(MatchingLike.builder().sendMemberNo(sendMemberNo).receiveMemberNo(reciveMemberNo).requestChannel('M').build());
    }
    
    public MatchingPicture getMemberPicture(Long mpNo) {
    	return matchingDao.getMemberPicture(mpNo);
    }

    public int deleteMatching(int mt_no) {
        return matchingDao.delete(mt_no);
    }
}
