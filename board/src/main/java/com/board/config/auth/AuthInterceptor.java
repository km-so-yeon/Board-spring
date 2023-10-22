package com.board.config.auth;

import com.board.config.exception.BaseException;
import com.board.config.exception.NotExistException;
import com.board.entity.Subject;
import com.board.member.mapper.MemberMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.JwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Objects;

import static com.board.constant.BaseStatus.*;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final JwtTokenProvider jwtTokenProvider;

    private final AuthorizationExtractor authExtractor;

    private MemberMapper memberMapper;

    @Autowired
    public AuthInterceptor(JwtTokenProvider jwtTokenProvider, AuthorizationExtractor authExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authExtractor = authExtractor;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // request로부터 토큰을 추출
        String atk = authExtractor.extract(request, "Bearer");
        if(Objects.isNull(atk)) {
            throw new NotExistException(AUTHORIZATION_NOT_EXIST);
        }
        // 유효한 토큰인지 확인
        if(!jwtTokenProvider.validateToken(atk)) {
            throw new NotExistException(INVALID_TOKEN);
        }

        // 토큰 디코딩
        Subject subject = jwtTokenProvider.getSubject(atk);
        // request에 디코딩한 값을 세팅
        request.setAttribute("subject", subject);

        return true;
    }

}
