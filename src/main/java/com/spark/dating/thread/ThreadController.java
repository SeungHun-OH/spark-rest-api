package com.spark.dating.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.spark.dating.dto.member.ApiResponse;
import com.spark.dating.dto.thread.BoardReply;
import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.dto.thread.response.ThreadBoardResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ThreadController {
  @Autowired
  ThreadService threadService;

  @PostMapping("/thread/board")
  public ApiResponse<Integer> insertThreadBoard(@RequestBody ThreadBoard threadBoard) {
    log.info("insertTreadBoard 콘트롤러 threadBoard멍미" + threadBoard.toString());
    try {
      int memberNo = threadService.insertThreadBoard(threadBoard);
      return new ApiResponse<>("success", "threadBaord 등록 성공", memberNo);
    } catch (Exception e) {
      return new ApiResponse<>("fail", "threadBaord 등록 실패" + e, 0);
    }
  }

  @GetMapping("/thread/boardList")
  public ApiResponse<List<ThreadBoardResponse>> getThreadBoardList() {
    try {
      List<ThreadBoardResponse> threadBoardList = threadService.getThreadBoardList();
      return new ApiResponse<>("success", "threadBoard 목록 조회 성공", threadBoardList);
    } catch (Exception e) {
      return new ApiResponse<>("fail", "threadBoard 목록 조회 실패" + e, null);
    }
  }


  @PostMapping("/thread/boardreply")
  public ApiResponse<Integer> insertBoardReply(@RequestBody BoardReply boardReply) {
    try {
      int memberNo = threadService.insertBoardReply(boardReply);
      return new ApiResponse<>("success", "BoardReply 등록 성공", memberNo);
    } catch (Exception e) {
      return new ApiResponse<>("fail", "threadBaord 등록 실패" + e, 0);
    } 
  }
}
