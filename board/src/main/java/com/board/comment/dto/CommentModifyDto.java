package com.board.comment.dto;

public class CommentModifyDto {

    private int boardId;

    private int postId;

    private int commentId;

    private String content;

    public int getBoardId() { return this.boardId; }

    public int getPostId() { return this.postId; }

    public int getCommentId() { return this.commentId; }
}
