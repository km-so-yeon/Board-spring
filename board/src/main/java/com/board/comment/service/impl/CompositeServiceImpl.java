package com.board.comment.service.impl;

import com.board.board.service.BoardService;
import com.board.comment.dto.CommentAddDto;
import com.board.comment.dto.CommentCreatedByDto;
import com.board.comment.mapper.CommentMapper;
import com.board.comment.service.CompositeService;
import com.board.config.response.BaseException;
import com.board.entity.Member;
import com.board.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.board.constant.BaseStatus.*;

/**
 * Composite 패턴 서비스 클래스
 * - PostService와 CommentService가 서로 호출하여 순환참조가 되지 않도록 Composite 패턴 사용
 * - CommentService 기능에서 다른 서비스들을 호출한다.
 */
@Service
public class CompositeServiceImpl implements CompositeService {

    private final CommentMapper commentMapper;

    private final BoardService boardService;

    private final PostService postService;

    CompositeServiceImpl(CommentMapper commentMapper, BoardService boardService, PostService postService) {
        this.commentMapper = commentMapper;
        this.boardService = boardService;
        this.postService = postService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addCommentDtl(CommentAddDto commentAddDto, Member member) {

        // 권한 확인하기
        if(boardService.haveBoardPermission(commentAddDto.getBoardId(), member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 댓글 수 추가하기
        postService.plusPostCommentCnt(commentAddDto.getBoardId(), commentAddDto.getPostId());

        commentMapper.insertComment(commentAddDto, member.getMemberId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
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

        // 댓글 수 빼기
        postService.subtractPostCommentCnt(boardId, postId);

        commentMapper.deleteComment(postId, commentId, member.getMemberId());
    }

}
