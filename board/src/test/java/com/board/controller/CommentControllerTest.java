package com.board.controller;

import com.board.comment.service.CommentService;
import com.board.comment.service.CompositeService;
import com.board.member.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// JUnit5와 Mockito 연동
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private CommentController target;

    // 테스트 대상에 주입시켜 줄 객체 (테스트 대상에서 사용할 객체)
    @Mock
    private CommentService commentService;

    @Mock
    private MemberService memberService;

    @Mock
    private CompositeService compositeService;

    // HTTP 호출 생성
    private MockMvc mockMvc;

    int boardId;
    int postId;

    @BeforeEach
    void beforeEach() {
        // 테스트를 시작할 때마다 실행
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

        boardId = 1;
        postId = 1;
    }

    @Test
    @DisplayName("댓글 조회")
    void 댓글_조회() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/boards/{boardId}/posts/{postId}/comments", boardId, postId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

}