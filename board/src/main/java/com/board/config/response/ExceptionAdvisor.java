package com.board.config.response;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.board.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvisor {
    @ExceptionHandler(BaseException.class)
    public BaseResponse exceptionHandler(BaseException e) {
        return new BaseResponse(e.getStatus());
    }
}
