package com.board.view.service;

import com.board.entity.Member;
import com.board.member.mapper.MemberMapper;
import com.board.view.ViewService;
import com.board.view.mapper.ViewMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ViewServiceImpl implements ViewService {

    private final ViewMapper viewMapper;

    ViewServiceImpl(ViewMapper viewMapper, MemberMapper memberMapper) {
        this.viewMapper = viewMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void insertViewPost(int boardId, int postId, Member member) {
        // 이미 읽었을 경우 insert하지 않음
        if(!viewMapper.isExistViewPost(boardId, postId, member.getMemberId())) {
            viewMapper.insertViewPost(boardId, postId, member.getMemberId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.SUPPORTS)
    public void insertViewComment(int boardId, int commentId, Member member) {
        // 이미 읽었을 경우 insert하지 않음
        if(!viewMapper.isExistViewComment(boardId, commentId, member.getMemberId())) {
            viewMapper.insertViewComment(boardId, commentId, member.getMemberId());
        }
    }
}
