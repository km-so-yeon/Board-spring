package com.board.token.service;

import com.board.token.dto.TokenDto;
import org.apache.ibatis.annotations.Param;

public interface TokenService {

    void insertToken(TokenDto tokenDto);

    void updateAccessToken(TokenDto tokenDto);

    String selectRtkByMemberEmail(@Param("email")String email);

    void deleteToken(@Param("accessToken")String accessToken);

}
