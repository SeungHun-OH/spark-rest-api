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

  public List<ThreadBoardResponse> getThreadBoardListByReplyCount() {
    List<ThreadBoardResponse> boards = threadDao.getThreadBoardList();
    // 댓글 수 기준 내림차순 정렬
    boards.sort((a, b) -> {
        int sizeA = (a.getBoardReplys() == null) ? 0 : a.getBoardReplys().size();
        int sizeB = (b.getBoardReplys() == null) ? 0 : b.getBoardReplys().size();
        return Integer.compare(sizeB, sizeA); // 내림차순
    });
    return boards;
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

          //  boardReplyDao.deleteBoardReply(tbNo);
           threadDao.deleteBoardRepliesByBoardNo(tbNo);
    return threadDao.deleteThreadBoard(tbNo);
  }

  public int updateThreadBoard(ThreadBoard threadBoard) {
    return threadDao.updateThreadBoard(threadBoard);
  }

  public List<ThreadBoardResponse> searchThreadBoards(String keyword) {
    return threadDao.searchThreadBoards(keyword);
  }

  public void deleteBoardReplyAll() {
    boardReplyDao.deleteBoardReplyAll();
  }
}


 // ThreadBoard GetBoard = threadDao.getThreadBoard(threadBoard.getTbNo());
    // log.info("수정 전 데이터" + GetBoard.toString());
    // log.info("수정 요청 데이터" + threadBoard.toString());



     // List<ThreadBoardResponse> boards =  threadDao.getThreadBoardList();
    // for(int i = 0; i< boards.size(); i++){
    //   int maxIndex = i;

    //   for(int j = i+1; j < boards.size(); j++){
    //     if(boards.get(j).getBoardReplys().size() > boards.get(maxIndex).getBoardReplys().size()){
    //       maxIndex = j;
    //     }
    //   }
    //   //swap
    //   ThreadBoardResponse tempBoard = boards.get(i);
    //   boards.set(i, boards.get(maxIndex));
    //   boards.set(maxIndex, tempBoard); 
    // }
    // return boards;