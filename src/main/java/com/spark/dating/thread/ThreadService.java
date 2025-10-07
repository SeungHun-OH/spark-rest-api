package com.spark.dating.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.thread.BoardReply;
import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.dto.thread.response.ThreadBoardResponse;

@Service
public class ThreadService {

  @Autowired
  ThreadDao threadDao;

  @Autowired
  BoardReplyDao boardReplyDao;

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
}
