package com.spark.dating.thread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.thread.ThreadBoard;
import com.spark.dating.dto.thread.response.ThreadBoardResponse;

@Mapper
public interface ThreadDao {

  int insertThreadBoard(ThreadBoard threadBoard);

  List<ThreadBoardResponse> getThreadBoardList();

  ThreadBoard getThreadBoard(int tbNo);

  int deleteThreadBoard(int tbNo);

  int deleteBoardReplysByBoardNo(int tbNo);

  int updateThreadBoard(ThreadBoard threadBoard);

  List<ThreadBoardResponse> searchThreadBoards(String keyword);

 
}
