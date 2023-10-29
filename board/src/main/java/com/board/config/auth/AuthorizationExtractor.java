package com.board.config.auth;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 헤더에서 토큰 추출
 * request의 header에서
 * Authorization 항목을 가져와서 토큰을 가져온다.
 */
@Component
public class AuthorizationExtractor {

    public static final String AUTHORIZATION = "Authorization";

    public String extract(HttpServletRequest request, String type) {
        Enumeration<String> headers = request.getHeaders(AUTHORIZATION);
        while(headers.hasMoreElements()) {
            String value = headers.nextElement();
            if(value.toLowerCase().startsWith(type.toLowerCase())) {
                // Bearer 을 빼고 가져와야 오류가 나지 않는다.
                return value.substring(type.length()).trim();
            }
        }

        return Strings.EMPTY;
    }

}
