package com.board.token.mapper;

import com.board.token.dto.TokenDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

@Mapper
public interface TokenMapper {

    void insertToken(TokenDto tokenDto);

    void updateAccessToken(TokenDto tokenDto);

    String selectRtkByMemberEmail(@Param("email")String email);

    void deleteToken(@Param("accessToken")String accessToken);
}
