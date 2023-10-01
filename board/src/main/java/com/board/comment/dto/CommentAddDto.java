package com.board.comment.dto;

public class CommentAddDto {

    private int boardId;

    private int postId;

    private String content;

    public int getBoardId() { return this.boardId; }

    public int getPostId() { return this.postId; }

    public String getContent() { return this.content; }
}
