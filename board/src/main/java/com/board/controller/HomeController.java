package com.board.controller;

import com.board.board.service.BoardService;
import com.board.config.response.BaseResponse;
import com.board.entity.Board;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class HomeController {

    private final BoardService boardService;

    HomeController(BoardService boardService) {
        this.boardService = boardService;
    }
    @GetMapping(value="/")
    public List<Board> main(HttpSession session, HttpServletRequest request) {
        return boardService.getBoardList(session, request);
    }
}
