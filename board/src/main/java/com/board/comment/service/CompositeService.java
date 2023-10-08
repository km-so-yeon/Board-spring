package com.board.comment.service;

import com.board.comment.dto.CommentAddDto;
import com.board.entity.Member;

public interface CompositeService {

    void addCommentDtl(CommentAddDto commentAddDto, Member member);

    void deleteCommentDtl(int boardId, int postId, int commentId, Member member);
}
