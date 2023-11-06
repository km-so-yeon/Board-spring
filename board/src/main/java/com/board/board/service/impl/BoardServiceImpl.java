package com.board.board.service.impl;

import com.board.board.mapper.BoardMapper;
import com.board.board.service.BoardService;
import com.board.entity.Board;
import com.board.entity.Member;
import com.board.member.mapper.MemberMapper;
import com.board.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;

    private final MemberService memberService;


    BoardServiceImpl(BoardMapper boardMapper, MemberService memberService) {
        this.boardMapper = boardMapper;
        this.memberService = memberService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Board> getBoardList(HttpSession session, HttpServletRequest request) {

        // 권한에 따라 가져오는 게시판 리스트가 다르다.
        // 현재 사용자의 정보 조회하기
        Member member = memberService.getMemberInfo(session, request);

        return boardMapper.selectBoardListByRoleId(member.getRoleId());
    }

    @Override
    @Transactional(readOnly = true)
    public boolean haveBoardPermission(int boardId, Member member) {
        return boardMapper.selectBoardPermission(boardId, member.getMemberId());
    }
}
