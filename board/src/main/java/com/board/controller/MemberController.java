package com.board.controller;

import com.board.config.response.BaseException;
import com.board.config.response.BaseResponse;
import com.board.entity.Member;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import com.board.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping(value="/member")
public class MemberController {

    private MemberService memberService;

    @Autowired
    MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * 회원가입
     * @param memberSignUpDto
     */
    @PostMapping(value="/sign-up")
    public void signUp(@RequestBody MemberSignUpDto memberSignUpDto, HttpServletRequest request) throws BaseException {

        memberService.signUp(memberSignUpDto, request);

    }

    /**
     * 로그인
     * @param memberLoginDto
     * @param request
     * @return
     */
    @PostMapping(value="/login")
    public Member login(@RequestBody MemberLoginDto memberLoginDto, HttpServletRequest request) throws BaseException {
        Member member = memberService.login(memberLoginDto);

        if(member != null) {
            HttpSession session = request.getSession();
            session.setAttribute("memberId", member.getMemberId());
            session.setAttribute("email", member.getEmail());
        }

        return member;
    }

    /**
     * 로그아웃
     * @param session
     */
    @PostMapping(value="/logout")
    public void logout(HttpSession session) {
        session.invalidate();
    }
}
