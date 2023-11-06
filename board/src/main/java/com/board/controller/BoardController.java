package com.board.controller;

import com.board.config.response.BaseResponse;
import com.board.entity.Post;
import com.board.post.service.PostService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value="/boards/{boardId}")
public class BoardController {

    private final PostService postService;

    BoardController(PostService postService) {
        this.postService = postService;
    }

    /**
     * 게시판 조회
     * @param boardId
     * @return 해당 게시판의 게시물 목록
     */
    @GetMapping
    public List<Post> getPostList(@PathVariable("boardId") int boardId) {
        return postService.getPostList(boardId);
    }
}
