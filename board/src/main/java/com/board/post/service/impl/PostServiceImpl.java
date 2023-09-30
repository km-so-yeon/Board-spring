package com.board.post.service.impl;

import com.board.board.service.BoardService;
import com.board.config.response.BaseException;
import com.board.entity.Member;
import com.board.entity.Post;
import com.board.post.dto.PostCreatedByDto;
import com.board.post.dto.PostDto;
import com.board.post.dto.PostModifyDto;
import com.board.post.mapper.PostMapper;
import com.board.post.service.PostService;
import com.board.view.ViewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.board.constant.BaseStatus.*;

@Service
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final ViewService viewService;

    private final BoardService boardService;

    PostServiceImpl(PostMapper postMapper, ViewService viewService, BoardService boardService) {
        this.postMapper = postMapper;
        this.viewService = viewService;
        this.boardService = boardService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> getPostList(int boardId) {
        return postMapper.selectPostList(boardId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Post getPostDtl(int boardId, int postId, Member member) throws BaseException {

        // 권한 확인하기
        if(boardService.haveBoardPermission(boardId, member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 조회수 올리기
        postMapper.updatePostReadCnt(boardId, postId);
        // 조회 테이블 insert
        viewService.insertViewPost(boardId, postId, member);

        return postMapper.selectPost(boardId, postId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addPostDtl(PostDto postDto, Member member) throws BaseException {

        // 권한 확인하기
        if(boardService.haveBoardPermission(postDto.getBoardId(), member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        postMapper.insertPost(postDto, member.getMemberId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void modifyPostDtl(PostModifyDto postModifyDto, Member member) throws BaseException {

        // 권한 확인하기
        if(boardService.haveBoardPermission(postModifyDto.getBoardId(), member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 게시글의 작성자 정보와 넘어온 회원 정보가 일치하는지 확인
        PostCreatedByDto postCreatedByDto = postMapper.selectPostCreatedBy(postModifyDto.getBoardId(), postModifyDto.getPostId());
        if(postCreatedByDto == null) {
            throw new BaseException(POST_NON_EXIST);
        }
        if(!postCreatedByDto.isMatch(member.getEmail(), member.getPassword())) {
            throw new BaseException(POST_CREATED_BY_NOT_MATCH);
        }

        postMapper.updatePost(postModifyDto, member.getMemberId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deletePostDtl(int boardId, int postId, Member member) throws BaseException {

        // 권한 확인하기
        if(boardService.haveBoardPermission(boardId, member)) {
            throw new BaseException(BOARD_NOT_PERMISSION);
        }

        // 게시글의 작성자 정보와 넘어온 회원 정보가 일치하는지 확인
        PostCreatedByDto postCreatedByDto = postMapper.selectPostCreatedBy(boardId, postId);
        if(postCreatedByDto == null) {
            throw new BaseException(POST_NON_EXIST);
        }
        if(!postCreatedByDto.isMatch(member.getEmail(), member.getPassword())) {
            throw new BaseException(POST_CREATED_BY_NOT_MATCH);
        }

        postMapper.deletePost(boardId, postId);
    }


}
