package com.board.controller;

import com.board.entity.Post;
import com.board.post.dto.PostDto;
import com.board.post.service.PostService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping(value="/boards/{boardId}/posts")
public class PostController {

    private final PostService postService;

    PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping(value="/{postId}")
    public Post getPostDtl(@PathVariable("boardId") int boardId, @PathVariable("postId") int postId) {
        return null;
    }

    @PostMapping
    public void addPostDtl(@PathVariable("boardId") int boardId, @RequestBody PostDto postDto, HttpSession session) {

    }

}
