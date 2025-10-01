package com.spark.dating.thread;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.dating.dto.thread.ThreadBoard;

@Service
public class ThreadService {

  @Autowired
  ThreadDao threadDao;

  public int insertThreadBoard(ThreadBoard threadBoard) {
    threadDao.insertThreadBoard(threadBoard);
    return threadBoard.getTbNo(); 
  }

  public List<ThreadBoard> getThreadBoardList() {
    return threadDao.getThreadBoardList();
  }
}
