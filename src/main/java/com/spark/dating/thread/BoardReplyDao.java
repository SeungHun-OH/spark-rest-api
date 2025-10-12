package com.spark.dating.thread;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.spark.dating.dto.thread.BoardReply;

@Mapper
public interface BoardReplyDao {
  int insertBoardReply(BoardReply boardReply);

  int deleteBoardReply(int brNo);

  int updateBoardReply(BoardReply boardReply);

  @Delete("DELETE FROM BOARDREPLY")
  void deleteBoardReplyAll();
}
