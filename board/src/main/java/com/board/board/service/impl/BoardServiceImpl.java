package com.board.board.service.impl;

import com.board.board.mapper.BoardMapper;
import com.board.board.service.BoardService;
import com.board.entity.Board;
import com.board.entity.Member;
import com.board.member.mapper.MemberMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    private final MemberMapper memberMapper;

    BoardServiceImpl(BoardMapper boardMapper, MemberMapper memberMapper) {
        this.boardMapper = boardMapper;
        this.memberMapper = memberMapper;
    }

    @Override
    public List<Board> getBoardList(HttpSession session) {

        // 권한에 따라 가져오는 게시판 리스트가 다르다.
        // 현재 사용자의 정보 조회하기
        int memberId = (Integer)session.getAttribute("memberId");
        Member member = memberMapper.selectMemberById(memberId);

        if(member == null) {
            // 로그인되어있지 않을 경우 비회원으로 설정해서 조회한다.
            member = new Member();
            member.setRoleIdGuest();
        }

        return boardMapper.selectBoardListByRoleId(member.getRoleId());
    }
}
