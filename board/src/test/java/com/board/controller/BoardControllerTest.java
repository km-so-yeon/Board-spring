package com.board.controller;

import com.board.config.response.ExceptionAdvisor;
import com.board.entity.Post;
import com.board.post.service.PostService;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// JUnit5와 Mockito 연동
@ExtendWith(MockitoExtension.class)
class BoardControllerTest {

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private BoardController target;

    // 테스트 대상에 주입시켜 줄 객체 (테스트 대상에서 사용할 객체)
    @Mock
    private PostService postService;

    // HTTP 호출 생성
    private MockMvc mockMvc;

    int boardId;

    @BeforeEach
    void beforeEach() {
        // 테스트를 시작할 때마다 실행
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();
        boardId = 1;
    }

    @AfterEach
    void afterEach() {
        // 테스트가 끝날 때마다 실행

    }

    @Test
    @DisplayName("게시물 목록 조회")
    void 게시물_목록_조회() throws Exception{
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/boards/{boardId}", boardId)
                        .accept(MediaType.APPLICATION_JSON)
                        )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

}