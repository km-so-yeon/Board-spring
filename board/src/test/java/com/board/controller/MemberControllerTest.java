package com.board.controller;

import com.board.member.dto.MemberLoginDto;
import com.board.member.dto.MemberSignUpDto;
import com.board.member.service.MemberService;
import com.board.member.service.impl.MemberServiceImpl;
import com.board.post.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// JUnit5와 Mockito 연동
@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private BoardController target;

    // 테스트 대상에 주입시켜 줄 객체 (테스트 대상에서 사용할 객체)
    @Mock
    private MemberService memberService;

    // HTTP 호출 생성
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private String email;

    private String password;

    @BeforeEach
    void beforeEach() {
        // 테스트를 시작할 때마다 실행
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
        email = "user2@gmail.com";
        password = "1234";
    }

    @AfterEach
    void afterEach() {
        // 테스트가 끝날 때마다 실행

    }

    @Test
    @DisplayName("회원가입")
    void 회원가입() throws Exception{

        MemberSignUpDto memberSignUpDto = getMemberSignUpDto();

        Map<String, Object> input = new HashMap<>();
        input.put("memberSignUpDto", memberSignUpDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/member/sign-up")
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("로그인")
    void 로그인() throws Exception {
        MemberLoginDto memberLoginDto = getMemberLoginDto();

        Map<String, Object> input = new HashMap<>();
        input.put("memberLoginDto", memberLoginDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/member/login")
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("로그아웃")
    void 로그아웃() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/member/logout")
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    private MemberSignUpDto getMemberSignUpDto() {
        return new MemberSignUpDto.Builder(email, password, "사용자2", 1)
                .ip("127.0.0.0")
                .build();
    }

    private MemberLoginDto getMemberLoginDto() {
        return new MemberLoginDto(email, password);
    }

}