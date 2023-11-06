package com.board.token.service.impl;

import com.board.token.dto.TokenDto;
import com.board.token.mapper.TokenMapper;
import com.board.token.service.TokenService;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {

    private final TokenMapper tokenMapper;

    public TokenServiceImpl(TokenMapper tokenMapper) {
        this.tokenMapper = tokenMapper;
    }

    @Override
    public void insertToken(TokenDto tokenDto) {
        tokenMapper.insertToken(tokenDto);
    }

    @Override
    public void updateAccessToken(TokenDto tokenDto) {
        tokenMapper.updateAccessToken(tokenDto);
    }

    @Override
    public String selectRtkByMemberEmail(String email) {
        return tokenMapper.selectRtkByMemberEmail(email);
    }

    @Override
    public void deleteToken(String accessToken) {
        tokenMapper.deleteToken(accessToken);
    }
}
