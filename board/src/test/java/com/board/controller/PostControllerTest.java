package com.board.controller;

import com.board.comment.service.CompositeService;
import com.board.member.dto.GuestDto;
import com.board.member.service.MemberService;
import com.board.post.dto.PostDto;
import com.board.post.dto.PostModifyDto;
import com.fasterxml.jackson.databind.ObjectMapper;
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
class PostControllerTest {

    // 테스트 대상 - 사용하는 객체들의 의존성이 주입된다.
    @InjectMocks
    private CommentController target;

    @Mock
    private MemberService memberService;

    @Mock
    private CompositeService compositeService;

    // HTTP 호출 생성
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

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
    @DisplayName("게시물 내용 조회")
    void 게시물_내용_조회() throws Exception{
        mockMvc.perform(
                        MockMvcRequestBuilders
                                .get("/boards/{boardId}/posts/{postId}", boardId, postId)
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("게시물 추가")
    void 게시물_추가() throws Exception{

        PostDto postDto = getPostDto();
        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("postDto", postDto);
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/boards/{boardId}/posts", boardId)
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("게시물 수정")
    void 게시물_수정() throws Exception{

        PostModifyDto postModifyDto = getPostModifyDto();
        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("postModifyDto", postModifyDto);
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/boards/{boardId}/posts/{postId}", boardId, postId)
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("게시물 삭제")
    void 게시물_삭제() throws Exception{

        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/boards/{boardId}/posts/{postId}", boardId, postId)
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    private PostDto getPostDto() {
        return new PostDto.Builder(boardId)
                .title("게시물 제목")
                .content("게시물 내용")
                .build();
    }

    private GuestDto getGuestDto() {
        return new GuestDto("user2@gmail.com", "1234");
    }

    private PostModifyDto getPostModifyDto() {
        return new PostModifyDto.Builder(boardId, postId)
                .title("댓글 제목")
                .content("댓글 내용")
                .build();
    }
}