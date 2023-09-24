package com.board.member.service;

import com.board.config.response.BaseException;
import com.board.entity.Member;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

public interface MemberService {

    void signUp(MemberSignUpDto memberSignUpDto) throws BaseException;

    Member login(MemberLoginDto memberLoginDto) throws BaseException;

}
