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
import com.board.post.mapper.PostMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

import static com.board.constant.BaseStatus.*;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;

    CommentServiceImpl(CommentMapper commentMapper, BoardService boardService) {
        this.commentMapper = commentMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public ArrayList<Comment> getCommentList(int boardId, int postId) {
        return commentMapper.selectCommentList(boardId, postId);
    }

}
