package com.board.config.response;

import com.board.config.exception.BaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static com.board.constant.BaseStatus.INTERNAL_SERVER_ERROR;

/**
 * 예외 처리
 * - 일관적인 에러응답 처리
 */
@RestControllerAdvice(basePackages = "com.board.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionAdvisor extends ResponseEntityExceptionHandler {

    private final Logger logger;

    public ExceptionAdvisor() {
        this.logger = LoggerFactory.getLogger(this.getClass());
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<Object> exceptionHandler(BaseException e, WebRequest request) {
        /*
         * e.printStackTrace() 대신 logback 사용
         * (스트림 내용을 즉시 flush하여 성능도 안좋고, 서버가 종료된 후 로그를 더 이상 확인할 수 없다.)
         */
        logger.error(e.getMessage(), e);

        return ResponseEntity.badRequest().body(new BaseResponse<>(e.getStatus(), getRequestInfo(request)));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(Exception e, WebRequest request) {
        HttpHeaders headers = new HttpHeaders();
        logger.error(e.getMessage(), e);

        if (e instanceof HttpRequestMethodNotSupportedException) {
            HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
            return handleHttpRequestMethodNotSupported((HttpRequestMethodNotSupportedException) e, headers, status, request);
        }

        return ResponseEntity.badRequest().body(new BaseResponse<>(INTERNAL_SERVER_ERROR, getRequestInfo(request)));
    }

    /**
     * - HttpRequestMethodNotSupportedException 발생 시 default 에러 객체를 내보내는 현상 해결
     * - ResponseEntityExceptionHandler 상속받아서 구현
     * @param ex the exception
     * @param headers the headers to be written to the response
     * @param status the selected response status
     * @param request the current request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported (
            HttpRequestMethodNotSupportedException ex,
            HttpHeaders headers, HttpStatus status, final WebRequest request) {

        logger.info("HttpRequestMethodNotSupported : ", ex);

        return ResponseEntity.badRequest().body(new BaseResponse<>(INTERNAL_SERVER_ERROR, getRequestInfo(request)));
    }

    private HashMap<String, Object> getRequestInfo(WebRequest request) {
        HttpServletRequest httpRequest = ((ServletWebRequest)request).getRequest();

        HashMap<String, Object> requestInfo = new HashMap<>();

        //requestInfo.put("uri", httpRequest.getRequestURI());
        requestInfo.put("requestURL", httpRequest.getRequestURL());
        requestInfo.put("method", httpRequest.getMethod());
        requestInfo.put("paramMap", httpRequest.getParameterMap());
        // postman에서 ContentType을 application/json 으로 보내서 request에서 찾을 수 없어서 나타나지 않음
        // jQuery의 ajax로 보낼 때는 디폴트로 application/x-www-form-urlencoded;charset=UTF-8로 지정되어서 확인 가능

        return requestInfo;
    }
}
