package com.board.config.response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.board.constant.BaseStatus.INTERNAL_SERVER_ERROR;

@RestControllerAdvice(basePackages = "com.board.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvisor {

    private final Logger logger;

    public ExceptionAdvisor() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse exceptionHandler(BaseException e) {
        /*
         * e.printStackTrace() 대신 logback 사용
         * (스트림 내용을 즉시 flush하여 성능도 안좋고, 서버가 종료된 후 로그를 더 이상 확인할 수 없다.)
         */
        logger.error(e.getMessage(), e);
        return new BaseResponse(e.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse exceptionHandler(Exception e) {
        logger.error(e.getMessage(), e);
        return new BaseResponse(INTERNAL_SERVER_ERROR);
    }
}
