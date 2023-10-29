package com.board.config;

import com.board.config.auth.AuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private final AuthInterceptor bearerAuthInterceptor;

    public WebMvcConfig(AuthInterceptor bearerAuthInterceptor) {
        this.bearerAuthInterceptor = bearerAuthInterceptor;
    }

    public void addInterceptors(InterceptorRegistry registry) {
        // home, post, comment Controller에서 요청 시 request로부터 token 추출
        // aop로 대체
        //registry.addInterceptor(bearerAuthInterceptor).addPathPatterns("/", "/post/**", "/comment/**");
    }

}
