package com.board.controller;

import com.board.config.auth.AuthorizationExtractor;
import com.board.config.auth.TokenResponse;
import com.board.config.exception.BaseException;
import com.board.config.exception.NotExistException;
import com.board.entity.Member;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import com.board.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Objects;

import static com.board.constant.BaseStatus.AUTHORIZATION_NOT_EXIST;

@RestController
@RequestMapping(value="/member")
public class MemberController {

    private MemberService memberService;

    private AuthorizationExtractor authExtractor;

    @Autowired
    MemberController(MemberService memberService, AuthorizationExtractor authExtractor) {
        this.memberService = memberService;
        this.authExtractor = authExtractor;
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
    public TokenResponse login(@RequestBody MemberLoginDto memberLoginDto, HttpServletRequest request) throws Exception {
        TokenResponse token = memberService.login(memberLoginDto);

        // 쿠키, 세션 로그인 방식
//        if(member != null) {
//            HttpSession session = request.getSession();
//            session.setAttribute("memberId", member.getMemberId());
//            session.setAttribute("email", member.getEmail());
//        }

        return token;
    }

    /**
     * JWT 토큰 재발급
     * @param memberLoginDto
     * @return
     * @throws Exception
     */
    @GetMapping("/reissue")
    public TokenResponse reissue(@RequestBody MemberLoginDto memberLoginDto) throws BaseException {
        return memberService.reissue(memberLoginDto);
    }

    /**
     * 로그아웃
     * @param request
     * @throws BaseException
     */
    @PostMapping(value="/logout")
    //public void logout(HttpSession session) { session.invalidate(); }
    public void logout(HttpServletRequest request) throws BaseException{
        // request로부터 토큰을 추출
        String atk = authExtractor.extract(request, "Bearer");
        if(Objects.isNull(atk)) {
            throw new NotExistException(AUTHORIZATION_NOT_EXIST);
        }
        memberService.logout(atk);
    }
}
