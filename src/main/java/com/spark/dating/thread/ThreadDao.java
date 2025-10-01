package com.spark.dating.thread;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.thread.ThreadBoard;

@Mapper
public interface ThreadDao {

  int insertThreadBoard(ThreadBoard threadBoard);

  List<ThreadBoard> getThreadBoardList();
}
