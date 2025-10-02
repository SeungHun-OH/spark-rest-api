package com.spark.dating.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.common.AuthenticationContextHolder;
import com.spark.dating.dto.feed.FeedPicture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/feedPicture")
public class FeedPictureController {
    @Autowired
    FeedPictureService feedPictureService;

    @GetMapping("/list")
    public List<FeedPicture> getFeedPictureList(@RequestParam("f_no") int f_no) {
        return feedPictureService.getPictures(f_no);
    }

    @GetMapping("/picture/{fp_no}")
    public ResponseEntity<byte[]> getFeedPicture(@PathVariable("fp_no") int fp_no) {
        FeedPicture feedPicture = feedPictureService.getPicture(fp_no);
        return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(feedPicture.getFpAttachtype()))
        .body(feedPicture.getFpAttachdata());
    }
    
    /*
        List<FeedPicture>로 리턴하면 jackson에서 feedPicture 객체를 json으로 직렬화하면서 byte[]
        필드를 Base64문자열로 바꿔서 내려줌
        -> 이미지 자체(byte[]
    */
    @GetMapping("/firstImg")
    public List<FeedPicture> getFirstImageofFeed() {
        int m_no = AuthenticationContextHolder.getContextMemberNo();
    	return feedPictureService.getFirstImgOfFeed(m_no);
    }
}
