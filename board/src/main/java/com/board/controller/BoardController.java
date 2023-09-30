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

    @GetMapping
    public BaseResponse<List<Post>> getPostList(@PathVariable("boardId") int boardId) {
        return new BaseResponse<>(postService.getPostList(boardId));
    }
}
