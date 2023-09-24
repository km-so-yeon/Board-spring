package com.board.post.service;

import com.board.entity.Post;
import com.board.post.dto.PostDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {

    List<Post> getPostList(int boardId);

    void addPostDtl(PostDto postDto, HttpSession session);
}
