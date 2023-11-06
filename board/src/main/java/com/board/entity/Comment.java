package com.board.entity;

import java.util.Date;

public class Comment {

    private int commentId;

    private int postId;

    private String content;

    private String useYn;

    private Date createdAt;

    private String createdByNm;

    public int getCommentId() {
        return commentId;
    }

    public int getPostId() {
        return postId;
    }

    public String getContent() {
        return this.content;
    }

    public String getUseYn() { return useYn; }

    public Date getCreatedAt() {
        return createdAt;
    }

    public String getCreatedByNm() {
        return createdByNm;
    }
}
