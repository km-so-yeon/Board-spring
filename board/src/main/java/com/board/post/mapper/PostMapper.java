package com.board.post.mapper;

import com.board.entity.Post;
import com.board.post.dto.PostCreatedByDto;
import com.board.post.dto.PostDto;
import com.board.post.dto.PostModifyDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    List<Post> selectPostList(@Param("boardId") int boardId);

    Post selectPost(@Param("boardId")int boardId, @Param("postId")int postId);

    void updatePostReadCnt(@Param("boardId")int boardId, @Param("postId")int postId);

    void insertPost(@Param("postDto")PostDto postDto, @Param("memberId")int memberId);

    void updatePost(@Param("postModifyDto")PostModifyDto postModifyDto, @Param("memberId")int memberId);

    PostCreatedByDto selectPostCreatedBy(@Param("boardId")int boardId, @Param("postId")int postId);

    void deletePost(@Param("boardId")int boardId, @Param("postId")int postId);
}
