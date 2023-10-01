package com.board.comment.mapper;

import com.board.comment.dto.CommentAddDto;
import com.board.comment.dto.CommentCreatedByDto;
import com.board.comment.dto.CommentModifyDto;
import com.board.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

@Mapper
public interface CommentMapper {

    ArrayList<Comment> selectCommentList(@Param("boardId")int boardId, @Param("postId")int postId);

    void insertComment(CommentAddDto commentAddDto, int memberId);

    CommentCreatedByDto selectCommentCreatedBy(@Param("postId")int postId, @Param("commentId")int commentId);

    void updateComment(CommentModifyDto commentModifyDto, @Param("memberId")int memberId);

    void deleteComment(@Param("postId")int postId, @Param("commentId")int commentId, @Param("memberId")int memberId);
}
