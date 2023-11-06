package com.board.board.service;

import com.board.entity.Board;
import com.board.entity.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public interface BoardService {

    List<Board> getBoardList(HttpSession session, HttpServletRequest request);

    boolean haveBoardPermission(int boardId, Member member);
}
