package com.board.entity;

import java.util.Date;

public class Post {

    private int postId;

    private int boardId;

    private String title;

    private int readCnt;

    private int commentCnt;

    private String useYn;

    private Date createdAt;

    private String createdByNm;

    public int getPostId() { return postId; }

    public int getBoardId() { return boardId; }

    public String getTitle() {
        return this.title;
    }

    public int getReadCnt() {
        return readCnt;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public String getUseYn() {
        return useYn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedByNm() {
        return createdByNm;
    }
}
