package com.spark.dating.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.spark.dating.dto.thread.BoardReply;
import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.dto.thread.response.ThreadBoardResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ThreadService {

  @Autowired
  ThreadDao threadDao;

  @Autowired
  BoardReplyDao boardReplyDao;

  // public ThreadBoard getThreadBoard(int tbNo) {
  //   return threadDao.getThreadBoard(tbNo);
  // }

  public int insertThreadBoard(ThreadBoard threadBoard) {
    threadDao.insertThreadBoard(threadBoard);
    return threadBoard.getTbNo(); 
  }

  public List<ThreadBoardResponse> getThreadBoardList() {
    return threadDao.getThreadBoardList();
  }

  public int insertBoardReply(BoardReply boardReply) {
    boardReplyDao.insertBoardReply(boardReply);
    return boardReply.getBrNo();
  }

  public int deleteBoardReply(int brNo) {
    return boardReplyDao.deleteBoardReply(brNo);
  }

  public int updateBoardReply(BoardReply boardReply) {
    return boardReplyDao.updateBoardReply(boardReply);
  }

  @Transactional // 댓글 + 본문 삭제 트랜젝션 처리!!
  public int deleteThreadBoard(int tbNo) {

           threadDao.deleteBoardReplysByBoardNo(tbNo);
    return threadDao.deleteThreadBoard(tbNo);
  }

  public int updateThreadBoard(ThreadBoard threadBoard) {

    ThreadBoard GetBoard = threadDao.getThreadBoard(threadBoard.getTbNo());
    log.info("수정 전 데이터" + GetBoard.toString());
    log.info("수정 요청 데이터" + threadBoard.toString());
    
    return threadDao.updateThreadBoard(threadBoard);
  }


}
