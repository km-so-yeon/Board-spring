package com.board.post.mapper;

import com.board.entity.Post;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PostMapper {

    List<Post> selectPostList(@Param("boardId") int boardId);
}
