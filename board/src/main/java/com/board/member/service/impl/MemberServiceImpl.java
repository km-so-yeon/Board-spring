package com.board.member.service.impl;


import com.board.config.auth.JwtTokenProvider;
import com.board.config.auth.TokenResponse;
import com.board.config.exception.*;

import static com.board.constant.BaseStatus.*;

import com.board.config.auth.redis.RedisDao;
import com.board.entity.Member;
import com.board.entity.Subject;
import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import com.board.member.mapper.MemberMapper;
import com.board.member.service.MemberService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final JwtTokenProvider jwtTokenProvider;

    private final ObjectMapper objectMapper;
    
    private final RedisDao redisDao;

    MemberServiceImpl(MemberMapper memberMapper
            , JwtTokenProvider jwtTokenProvider
            , ObjectMapper objectMapper
            , RedisDao redisDao) {
        this.memberMapper = memberMapper;
        this.jwtTokenProvider = jwtTokenProvider;
        this.objectMapper = objectMapper;
        this.redisDao = redisDao;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void signUp(MemberSignUpDto memberSignUpDto, HttpServletRequest request) throws BaseException{

        // 같은 이메일의 회원이 존재하는지 확인
        if (memberMapper.selectMemberByEmail(memberSignUpDto.getEmail()) != null) {
            // exception 이미 등록된 회원입니다
            throw new DuplicateDataException(MEMBER_DUPLICATE_EMAIL);
        }
        // 같은 IP의 회원이 존재하는지 확인
        String clientIp = this.getClientIp(request);
        if (memberMapper.selectMemberByIp(clientIp) != null) {
            // exception 이미 등록된 회원입니다
            throw new DuplicateDataException(MEMBER_DUPLICATE_IP);
        }

        // ip 설정
        memberSignUpDto.setIp(clientIp);

        // 회원 등록
        memberMapper.insertMember(memberSignUpDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TokenResponse login(MemberLoginDto memberLoginDto) throws BaseException {

        // 회원 이메일, 비밀번호 확인
        Member member = memberMapper.selectMemberByEmail(memberLoginDto.getEmail());

        if(member == null) {
            // exception 존재하지 않는 회원입니다
            throw new NotExistException(MEMBER_NOT_EXIST);
        }
        if(!member.getPassword().equals(memberLoginDto.getPassword())) {
            // exception 비밀번호가 틀립니다
            throw new NotMatchException(MEMBER_PASSWORD_WRONG);
        }

        // 멤버 정보로 jwt 토큰 발급
        return jwtTokenProvider.createTokenByLogin(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TokenResponse reissue(MemberLoginDto memberLoginDto) throws BaseException {
        // 회원 정보 가져오기
        Member member = memberMapper.selectMemberByEmail(memberLoginDto.getEmail());
        if(member == null) {
            // exception 존재하지 않는 회원입니다
            throw new NotExistException(MEMBER_NOT_EXIST);
        }
        return jwtTokenProvider.reissueAtk(member);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Member getMemberInfo(HttpSession session, HttpServletRequest request) throws BaseException {

        Member member = null;

        /* 쿠키, 세션 로그인 방식
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
         */

        try {
            String subjectStr = (String) request.getAttribute("subject");

            if(subjectStr == null) {
                // 비회원일 때
                String ip = this.getClientIp(request);
                member = memberMapper.selectMemberByIp(ip);

                if(member == null) {
                    memberMapper.insertGuest(ip);
                    member = memberMapper.selectMemberByIp(ip);
                }

            } else {
                // 회원일 때
                Subject subject = objectMapper.readValue(subjectStr, Subject.class);
                String email = subject.getEmail();
                member = memberMapper.selectMemberByEmail(email);
            }
        } catch (JsonProcessingException e) {
            throw new NotPermitException(INVALID_TOKEN);
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

    @Override
    public void logout(String atk) throws BaseException{
        jwtTokenProvider.deleteToken(atk);
    }

}
