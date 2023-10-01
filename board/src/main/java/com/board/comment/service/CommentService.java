package com.board.comment.service;

import com.board.comment.dto.CommentAddDto;
import com.board.comment.dto.CommentModifyDto;
import com.board.entity.Comment;
import com.board.entity.Member;

import java.util.ArrayList;

public interface CommentService {

    ArrayList<Comment> getCommentList(int boardId, int postId);

    void addCommentDtl(CommentAddDto commentAddDto, Member member);

    void modifyCommentDtl(CommentModifyDto commentModifyDto, Member member);

    void deleteCommentDtl(int boardId, int postId, int commentId, Member member);

}
