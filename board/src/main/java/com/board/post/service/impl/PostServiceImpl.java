package com.board.post.service.impl;

import com.board.entity.Member;
import com.board.entity.Post;
import com.board.member.mapper.MemberMapper;
import com.board.post.dto.PostDto;
import com.board.post.mapper.PostMapper;
import com.board.post.service.PostService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional
public class PostServiceImpl implements PostService {

    private final PostMapper postMapper;

    private final MemberMapper memberMapper;

    PostServiceImpl(PostMapper postMapper, MemberMapper memberMapper) {
        this.postMapper = postMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public List<Post> getPostList(int boardId) {
        return postMapper.selectPostList(boardId);
    }

    @Override
    public void addPostDtl(PostDto postDto, HttpSession session) {

        // 사용자 정보 가져오기
        int memberId = (Integer)session.getAttribute("memberId");
        Member member = memberMapper.selectMemberById(memberId);

        // 비회원 등록이 가능해야하므로 사용자 없을 경우 생성
        if(member == null) {
            member = new Member();
            member.setRoleIdGuest();
        }
    }


}
