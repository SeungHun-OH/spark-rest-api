package com.spark.dating.thread;

import org.apache.ibatis.annotations.Mapper;

import com.spark.dating.dto.thread.BoardReply;

@Mapper
public interface BoardReplyDao {
  int insertBoardReply(BoardReply boardReply);

  int deleteBoardReply(int brNo);

  int updateBoardReply(BoardReply boardReply);
}
