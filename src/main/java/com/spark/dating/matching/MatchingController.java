package com.spark.dating.matching;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.feed.FeedPicture;
import com.spark.dating.dto.matching.Matching;
import com.spark.dating.dto.matching.MatchingPicture;
import com.spark.dating.dto.member.MemberPicture;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/matching")
@Slf4j
//매칭 create -> 매칭번호 어디에 전송? chatController (axios 사용해서)
public class MatchingController {

    @Autowired
    private MatchingService matchingService;
    /*
        - 매칭 테이블 생성
            - 좋아요 화면에서 나에게 온 좋아요 수락할 경우 (좋아요 있는지 확인 후 생성?)
        - 개별 회원의 매칭 테이블 get (feedLike,m)
        - matching의 카드 생성 부분만 중복되지 않게 처리하면 됨 -> 매칭된 사람들은 나오지 않도록
    */
    
    //뭐가 필요하지? 우선 회원번호 필요, 받은 회원번호 필요, 경로도 필요
//    @PostMapping("/")
//    public void createMatching(@RequestParam("h_no") int h_no) {
//        matchingService.createMatching(h_no);
//    }
    
    @GetMapping("/")
    //매칭 테이블을 모두 가지고 올 이유가 있을까? 해당 하는 매칭 테이블만 가지고 오면 될 거 같은데
    public List<Matching> createMatching() {
        final int memberNo = AuthenticationContextHolder.getContextMemberNo();
    	return matchingService.getMatching(memberNo);
    }
    
    @PostMapping("/like")
    public void matchingPostLike(@RequestBody String partnerUuid) {
    	final int memberNo = AuthenticationContextHolder.getContextMemberNo();
    	matchingService.matchingPostLike(memberNo ,partnerUuid);
    }
    

    @DeleteMapping("/")
    public int deleteMatching(@RequestParam("mt_no") int mt_no) {
        return matchingService.deleteMatching(mt_no);
    }
    
    @GetMapping("/picture/{memberPictureNo}")
    public ResponseEntity<byte[]> getFeedPicture(@PathVariable("memberPictureNo") Long mpNo) {
    	MatchingPicture matchingPicture = matchingService.getMemberPicture(mpNo);
        return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(matchingPicture.getAttachType()))
        .body(matchingPicture.getData());
    }
    
}
