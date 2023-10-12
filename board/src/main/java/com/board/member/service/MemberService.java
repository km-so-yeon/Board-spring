package com.board.member.service;

import com.board.config.exception.BaseException;
import com.board.entity.Member;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface MemberService {

    void signUp(MemberSignUpDto memberSignUpDto, HttpServletRequest request) throws BaseException;

    Member login(MemberLoginDto memberLoginDto) throws BaseException;

    Member getMemberInfo(HttpSession session, HttpServletRequest request) throws BaseException;

    String getClientIp(HttpServletRequest request);
}
