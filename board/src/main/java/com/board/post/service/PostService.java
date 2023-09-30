package com.board.post.service;

import com.board.entity.Member;
import com.board.entity.Post;
import com.board.member.dto.GuestDto;
import com.board.post.dto.PostDto;
import com.board.post.dto.PostModifyDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {

    List<Post> getPostList(int boardId);

    Post getPostDtl(int boardId, int postId, Member member);

    void addPostDtl(PostDto postDto, Member member);

    void modifyPostDtl(PostModifyDto postModifyDto, Member member);

    void deletePostDtl(int boardId, int postId, Member member);
}
