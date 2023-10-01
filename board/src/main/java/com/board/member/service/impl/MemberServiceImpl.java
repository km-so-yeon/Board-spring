package com.board.member.service.impl;


import com.board.config.response.BaseException;
import static com.board.constant.BaseStatus.*;
import com.board.entity.Member;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import com.board.member.mapper.MemberMapper;
import com.board.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signUp(MemberSignUpDto memberSignUpDto, HttpServletRequest request) throws BaseException{

        // 같은 이메일의 회원이 존재하는지 확인
        if (memberMapper.selectMemberByEmail(memberSignUpDto.getEmail()) != null) {
            // exception 이미 등록된 회원입니다
            throw new BaseException(MEMBER_DUPLICATE_EMAIL);
        }
        // 같은 IP의 회원이 존재하는지 확인
        String clientIp = this.getClientIp(request);
        if (memberMapper.selectMemberByIp(clientIp) != null) {
            // exception 이미 등록된 회원입니다
            throw new BaseException(MEMBER_DUPLICATE_IP);
        }

        // ip 설정
        memberSignUpDto.setIp(clientIp);

        // 회원 등록
        memberMapper.insertMember(memberSignUpDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Member login(MemberLoginDto memberLoginDto) throws BaseException{

        // 회원 이메일, 비밀번호 확인
        Member member = memberMapper.selectMemberByEmail(memberLoginDto.getEmail());

        if(member == null) {
            // exception 존재하지 않는 회원입니다
            throw new BaseException(MEMBER_NON_EXIST);
        }
        if(!member.getPassword().equals(memberLoginDto.getPassword())) {
            // exception 비밀번호가 틀립니다
            throw new BaseException(MEMBER_PASSWORD_WORNG);
        }
        member.clearPassword(); // 비밀번호 제거 후 리턴

        return member;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member getMemberInfo(HttpSession session, HttpServletRequest request) throws BaseException {

        Member member = null;

        Integer memberId = (Integer)session.getAttribute("memberId");
        if(memberId != null) {
            member = memberMapper.selectMemberById(memberId);

        } else {
            String ip = this.getClientIp(request);
            member = memberMapper.selectMemberByIp(ip);

            if(member == null) {
                memberMapper.insertGuest(ip);
                member = memberMapper.selectMemberByIp(ip);
            }

        }

        return member;
    }

    @Override
    public String getClientIp(HttpServletRequest request) {
        String clientIp = null;

        List<String> headerList = new ArrayList<>();
        headerList.add("X-Forwarded-For");
        headerList.add("HTTP_CLIENT_IP");
        headerList.add("HTTP_X_FORWARDED_FOR");
        headerList.add("HTTP_X_FORWARDED");
        headerList.add("HTTP_FORWARDED_FOR");
        headerList.add("HTTP_FORWARDED");
        headerList.add("Proxy-Client-IP");
        headerList.add("WL-Proxy-Client-IP");
        headerList.add("HTTP_VIA");
        headerList.add("IPV6_ADR");

        for (String header : headerList) {
            clientIp = request.getHeader(header);
            if (StringUtils.hasText(clientIp) && !clientIp.equals("unknown")) {
                break;
            }
        }

        return clientIp;
    }

}
