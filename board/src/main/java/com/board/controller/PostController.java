package com.board.controller;

import com.board.config.exception.BaseException;
import com.board.config.exception.IsBlankException;
import com.board.entity.Member;
import com.board.entity.Post;
import com.board.member.dto.GuestDto;
import com.board.member.service.MemberService;
import com.board.post.dto.PostDto;
import com.board.post.dto.PostModifyDto;
import com.board.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static com.board.constant.BaseStatus.GUEST_IS_BLANK;

@RestController
@RequestMapping(value="/boards/{boardId}/posts")
public class PostController {

    private final PostService postService;

    private final MemberService memberService;

    PostController(PostService postService, MemberService memberService) {
        this.postService = postService;
        this.memberService = memberService;
    }

    @GetMapping(value="/{postId}")
    public Post getPostDtl(@PathVariable("boardId") int boardId
                         , @PathVariable("postId") int postId
                         , HttpSession session, HttpServletRequest request) {

        // 현재 사용자 정보 가져오기
        Member member = memberService.getMemberInfo(session, request);

        // 게시글 조회하기
        return postService.getPostDtl(boardId, postId, member);
    }

    @PostMapping
    public void addPostDtl(@RequestBody PostDto postDto
                         , @RequestBody GuestDto guestDto
                         , HttpSession session, HttpServletRequest request) throws BaseException {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            if(guestDto.isEmpty()) {
                throw new IsBlankException(GUEST_IS_BLANK);
            }

            member.setGuestDtl(guestDto);
        }

        postService.addPostDtl(postDto, member);
    }

    @PutMapping(value="/{postId}")
    public void modifyPostDtl(@RequestBody PostModifyDto postModifyDto
                            , @RequestBody GuestDto guestDto
                            , HttpSession session, HttpServletRequest request) throws BaseException {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            // 비회원 이메일 및 비밀번호가 없을 경우
            if(guestDto.isEmpty()) {
                throw new IsBlankException(GUEST_IS_BLANK);
            }

            member.setGuestDtl(guestDto);
        }

        postService.modifyPostDtl(postModifyDto, member);
    }

    @DeleteMapping(value="/{postId}")
    public void deletePostDtl(@PathVariable("boardId") int boardId
                            , @PathVariable("postId") int postId
                            , @RequestBody GuestDto guestDto
                            , HttpSession session, HttpServletRequest request) throws BaseException {

        // 회원 정보가 있는지 확인
        Member member = memberService.getMemberInfo(session, request);

        // 회원정보가 없을 경우
        if(!member.isExist()) {
            // 비회원 이메일 및 비밀번호가 없을 경우
            if(guestDto.isEmpty()) {
                throw new IsBlankException(GUEST_IS_BLANK);
            }

            member.setGuestDtl(guestDto);
        }

        postService.deletePostDtl(boardId, postId, member);
    }

}
