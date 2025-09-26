package com.spark.dating.feed;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.feed.FeedPicture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/feedPicture")
public class FeedPictureController {
    @Autowired
    FeedPictureService feedPictureService;

    @GetMapping("/list")
    public List<FeedPicture> getFeedPictures(@RequestParam("f_no") int f_no) {
        return feedPictureService.getPictures(f_no);
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> getFeedPicture(@RequestParam("fp_no") int fp_no) {
        FeedPicture feedPicture = feedPictureService.getPicture(fp_no);
        return ResponseEntity
        .ok()
        .contentType(MediaType.parseMediaType(feedPicture.getFpAttachtype()))
        .body(feedPicture.getFpAttachdata());
    } 
}
