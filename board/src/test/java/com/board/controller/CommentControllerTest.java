package com.board.controller;

import com.board.comment.dto.CommentModifyDto;
import com.board.comment.service.CommentService;
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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.Map;

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

    @Autowired
    private ObjectMapper objectMapper;

    int boardId;
    int postId;
    int commentId;

    @BeforeEach
    void beforeEach() {
        // 테스트를 시작할 때마다 실행
        mockMvc = MockMvcBuilders.standaloneSetup(target).build();

        boardId = 1;
        postId = 1;
        commentId = 1;
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

    @Test
    @DisplayName("댓글 추가")
    void 댓글_추가() throws Exception{

        PostDto postDto = getPostDto();
        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("postDto", postDto);
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .post("/boards/{boardId}/posts/{postId}/comments", boardId, postId)
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("댓글 수정")
    void 댓글_수정() throws Exception{

        CommentModifyDto commentModifyDto = getCommentModifyDto();
        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("commentModifyDto", commentModifyDto);
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .put("/boards/{boardId}/posts/{postId}/comments/{commentId}", boardId, postId)
                                .contentType(MediaType.APPLICATION_JSON)    // json으로 보낸다고 명시
                                .content(objectMapper.writeValueAsString(input))
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk()) // 상태코드 검증
                .andDo(print()) // 요청/응답 전체메시지 확인
        ;
    }

    @Test
    @DisplayName("댓글 삭제")
    void 댓글_삭제() throws Exception{

        GuestDto guestDto = getGuestDto();

        Map<String, Object> input = new HashMap<>();
        input.put("guestDto", guestDto);

        mockMvc.perform(
                        MockMvcRequestBuilders
                                .delete("/boards/{boardId}/posts/{postId}/comments/{commentId}", boardId, postId, commentId)
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
                .title("댓글 제목")
                .content("댓글 내용")
                .build();
    }

    private GuestDto getGuestDto() {
        return new GuestDto("user2@gmail.com", "1234");
    }

    private CommentModifyDto getCommentModifyDto() {
        return null;
    }
}