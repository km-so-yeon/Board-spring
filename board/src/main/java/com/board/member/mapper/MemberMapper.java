package com.board.member.mapper;

import com.board.entity.Member;
import com.board.member.dto.MemberSignUpDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {

    Member selectMemberByEmail(@Param("email")String email);

    void insertMember(MemberSignUpDto memberSignUpDto);

    Member selectMemberById(@Param("memberId")int memberId);
}
