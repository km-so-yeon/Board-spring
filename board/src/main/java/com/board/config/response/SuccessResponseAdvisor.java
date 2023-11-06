package com.board.config.response;

import com.board.config.exception.BaseException;
import org.springframework.core.MethodParameter;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletResponse;

@RestControllerAdvice(basePackages = "com.board.controller")
public class SuccessResponseAdvisor<T> implements ResponseBodyAdvice<T> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {

        if(body instanceof BaseException) {
            BaseException baseException = (BaseException) body;
            //return new BaseResponse(baseException);
        }

        //return ResponseEntity.ok().body(new BaseResponse<>(body));
        return new BaseResponse<>(body);
    }
}
