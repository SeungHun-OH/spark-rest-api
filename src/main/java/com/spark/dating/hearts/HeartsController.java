package com.spark.dating.hearts;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.chat.service.ChatRoomService;
import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.hearts.Hearts;
import com.spark.dating.dto.hearts.HeartsRequest;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/hearts")
@Slf4j
public class HeartsController {
    /*
        - 관심 테이블 생성
            - 타인의 피드에서 좋아요를 눌렀을 때 | 매칭 부분에서 좋아요 눌렀을 때
                -> 매칭 부분에 그 사람 안 나옴 + 그 사람 피드 좋아요 비활성화
        - 관심 테이블 삭제
            - 타인의 피드에서 좋아요를 삭제할 때
            - 좋아요 페이지에서 카드를 거절했을 때
        - 내가 좋아요한 프로필 리스트 (추후에 구현)
        - 나를 좋아요한 프로필 리스트
    */
	
    @Autowired
    private HeartsService heartsService;
    
    @Autowired
    private ChatRoomService chatRoomService;

//    @PostMapping("/")
//    public Hearts createHearts(@RequestBody Hearts hearts) {
//        return heartsService.createHearts(hearts);
//    }
    @PostMapping("/{heartsNo}/accept")
    public void acceptHeartRequest(@PathVariable("heartsNo") Long heartsNo) {
    	final int memberNo = AuthenticationContextHolder.getContextMemberNo();
//    	System.out.println();
		chatRoomService.createChatRoom(heartsNo, memberNo);
    	
    	//내일 이거만 이어서 하면 될듯
    	// 채팅방 연동확인
//    	return heartsService.createHearts(hearts);
    }

    //나를 좋아요한 리스트 //requestChanel에 따라 변경 가능
    @GetMapping("/")
    public List<Hearts> getHearts() 
    {
    	final int memberNo = AuthenticationContextHolder.getContextMemberNo();
        return heartsService.getHearts(memberNo);
    }

    @DeleteMapping("/{heartsNo}/reject")
    public void injectHeartRequest(@PathVariable("heartsNo") Long heartsNo) {
        heartsService.injectHeartRequest(heartsNo);
    }
}
