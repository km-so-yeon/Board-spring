package com.board.board.service;

import com.board.entity.Board;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface BoardService {

    List<Board> getBoardList(HttpSession session);

}
