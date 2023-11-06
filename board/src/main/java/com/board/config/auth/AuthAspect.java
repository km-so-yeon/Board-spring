package com.board.config.auth;

import com.board.config.exception.BaseException;
import com.board.config.exception.NotExistException;
import com.board.entity.Subject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

import static com.board.constant.BaseStatus.AUTHORIZATION_NOT_EXIST;
import static com.board.constant.BaseStatus.INVALID_TOKEN;

@Aspect
@Component
public class AuthAspect {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthorizationExtractor authExtractor;

    public AuthAspect(JwtTokenProvider jwtTokenProvider
            , AuthorizationExtractor authExtractor) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authExtractor = authExtractor;
    }

    @Before("execution(* com.board.controller.*.*Post*(..) ) || " +
            "execution(* com.board.controller.*.*Comment*(..) )")
    public void authCheck(JoinPoint joinPoint) throws BaseException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        // request로부터 토큰을 추출
        String atk = authExtractor.extract(request, "Bearer");
        if(Objects.isNull(atk) || atk.isBlank()) {
            //throw new NotExistException(AUTHORIZATION_NOT_EXIST);
            return;
        }
        // 유효한 토큰인지 확인
        if(!jwtTokenProvider.validateToken(atk)) {
            //throw new NotExistException(INVALID_TOKEN);
            return;
        }

        // 토큰 디코딩
        Subject subject = jwtTokenProvider.getSubject(atk);
        // request에 디코딩한 값을 세팅
        request.setAttribute("subject", subject);
    }

}
