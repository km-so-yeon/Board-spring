package com.board.view.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ViewMapper {

    boolean isExistViewPost(@Param("boardId") int boardId, @Param("postId") int postId, @Param("memberId") int memberId);
    void insertViewPost(@Param("boardId") int boardId, @Param("postId") int postId, @Param("memberId") int memberId);

    boolean isExistViewComment(@Param("boardId") int boardId, @Param("commentId") int commentId, @Param("memberId") int memberId);
    void insertViewComment(@Param("boardId") int boardId, @Param("commentId") int commentId, @Param("memberId") int memberId);
}
