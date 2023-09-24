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

import javax.servlet.http.HttpSession;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;

    MemberServiceImpl(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    @Override
    public void signUp(MemberSignUpDto memberSignUpDto) throws BaseException{
        // 같은 이메일의 회원이 존재하는지 확인
        Member member = memberMapper.selectMemberByEmail(memberSignUpDto.getEmail());

        if (member != null) {
            // exception 이미 등록된 회원입니다
            throw new BaseException(MEMBER_DUPLICATE_EMAIL);
        }

        // 회원 등록
        memberMapper.insertMember(memberSignUpDto);
    }

    @Override
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

}
