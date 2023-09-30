package com.board.view;

import com.board.entity.Member;
import org.springframework.stereotype.Service;

public interface ViewService {

    void insertViewPost(int boardId, int postId, Member member);

    void insertViewComment(int boardId, int commentId, Member member);

}
