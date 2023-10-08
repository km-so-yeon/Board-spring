package com.board.controller;

import com.board.comment.dto.CommentAddDto;
import com.board.comment.dto.CommentModifyDto;
import com.board.comment.service.CommentService;
import com.board.comment.service.CompositeService;
import com.board.config.response.BaseException;
import com.board.config.response.BaseResponse;
import com.board.entity.Comment;
import com.board.entity.Member;
import com.board.member.dto.GuestDto;
import com.board.member.service.MemberService;
import com.board.post.dto.PostDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

import static com.board.constant.BaseStatus.GUEST_IS_BLANK;

@RestController
@RequestMapping(value="/boards/{boardId}/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    private final MemberService memberService;

    private final CompositeService compositeService;

    CommentController(CommentService commentService, MemberService memberService, CompositeService compositeService) {
        this.commentService = commentService;
        this.memberService = memberService;
        this.compositeService = compositeService;
    }

    @GetMapping
    public ArrayList<Comment> getCommentList(@PathVariable("boardId")int boardId
                                                         , @PathVariable("postId")int postId) {
        // TODO 댓글 페이징 처리
        return commentService.getCommentList(boardId, postId);
    }

    @PostMapping
    public void addCommentDtl(@RequestBody CommentAddDto commentAddDto
                                            , @RequestBody GuestDto guestDto
                                            , HttpSession session, HttpServletRequest request) {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            // 비회원 이메일 및 비밀번호가 없을 경우
            if(guestDto.isEmpty()) {
                throw new BaseException(GUEST_IS_BLANK);
            }

            member.setEmail(guestDto.getEmail());
            member.setPassword(guestDto.getPassword());
        }

        compositeService.addCommentDtl(commentAddDto, member);
    }

    @PutMapping(value="/{commentId}")
    public void updateCommentDtl(@RequestBody CommentModifyDto commentModifyDto
                                               , @RequestBody GuestDto guestDto
                                               , HttpSession session, HttpServletRequest request) {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            // 비회원 이메일 및 비밀번호가 없을 경우
            if(guestDto.isEmpty()) {
                throw new BaseException(GUEST_IS_BLANK);
            }

            member.setEmail(guestDto.getEmail());
            member.setPassword(guestDto.getPassword());
        }

        commentService.modifyCommentDtl(commentModifyDto, member);
    }

    @DeleteMapping(value="/{commentId}")
    public void deleteCommentDtl(@PathVariable("boardId") int boardId
                                               , @PathVariable("postId") int postId
                                               , @PathVariable("commentId") int commentId
                                               , @RequestBody GuestDto guestDto
                                               , HttpSession session, HttpServletRequest request) {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            // 비회원 이메일 및 비밀번호가 없을 경우
            if(guestDto.isEmpty()) {
                throw new BaseException(GUEST_IS_BLANK);
            }

            member.setEmail(guestDto.getEmail());
            member.setPassword(guestDto.getPassword());
        }

        compositeService.deleteCommentDtl(boardId, postId, commentId, member);
    }
}
