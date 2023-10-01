package com.board.comment.service.impl;

import com.board.board.service.BoardService;
import com.board.comment.dto.CommentAddDto;
import com.board.comment.dto.CommentCreatedByDto;
import com.board.comment.dto.CommentModifyDto;
import com.board.comment.mapper.CommentMapper;
import com.board.comment.service.CommentService;
import com.board.config.response.BaseException;
import com.board.entity.Board;
import com.board.entity.Comment;
import com.board.entity.Member;
import com.board.post.dto.PostCreatedByDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import static com.board.constant.BaseStatus.*;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    private final BoardService boardService;

    CommentServiceImpl(CommentMapper commentMapper, BoardService boardService) {
        this.commentMapper = commentMapper;
        this.boardService = boardService;
    }

    @Override
    public ArrayList<Comment> getCommentList(int boardId, int postId) {
        return commentMapper.selectCommentList(boardId, postId);
    }

    @Override
    public void addCommentDtl(CommentAddDto commentAddDto, Member member) {

        // 권한 확인하기
        if(boardService.haveBoardPermission(commentAddDto.getBoardId(), member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        commentMapper.insertComment(commentAddDto, member.getMemberId());
    }

    @Override
    public void modifyCommentDtl(CommentModifyDto commentModifyDto, Member member) {

        // 권한 확인하기
        if(boardService.haveBoardPermission(commentModifyDto.getBoardId(), member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 댓글의 작성자 정보와 넘어온 회원 정보가 일치하는지 확인
        CommentCreatedByDto commentCreatedByDto = commentMapper.selectCommentCreatedBy(commentModifyDto.getPostId(), commentModifyDto.getCommentId());
        if(commentCreatedByDto == null) {
            throw new BaseException(COMMENT_NON_EXIST);
        }
        if(!commentCreatedByDto.isMatch(member.getEmail(), member.getPassword())) {
            throw new BaseException(COMMENT_CREATED_BY_NOT_MATCH);
        }

        commentMapper.updateComment(commentModifyDto, member.getMemberId());
    }

    @Override
    public void deleteCommentDtl(int boardId, int postId, int commentId, Member member) {

        // 권한 확인하기
        if(boardService.haveBoardPermission(boardId, member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 댓글의 작성자 정보와 넘어온 회원 정보가 일치하는지 확인
        CommentCreatedByDto commentCreatedByDto = commentMapper.selectCommentCreatedBy(postId, commentId);
        if(commentCreatedByDto == null) {
            throw new BaseException(COMMENT_NON_EXIST);
        }
        if(!commentCreatedByDto.isMatch(member.getEmail(), member.getPassword())) {
            throw new BaseException(COMMENT_CREATED_BY_NOT_MATCH);
        }

        commentMapper.deleteComment(postId, commentId, member.getMemberId());
    }
}
