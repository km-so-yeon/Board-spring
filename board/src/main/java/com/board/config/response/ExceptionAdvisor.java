package com.board.config.response;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(BaseException.class)
    public BaseResponse exceptionHandler(BaseException e) {
        return new BaseResponse(e.getStatus());
    }
}
