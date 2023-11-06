package com.board.comment.dto;

public class CommentModifyDto {

    private int boardId;

    private int postId;

    private int commentId;

    private String content;

    public int getBoardId() { return this.boardId; }

    public int getPostId() { return this.postId; }

    public int getCommentId() { return this.commentId; }

    public CommentModifyDto() {}

    public static class Builder{
        // Required parameters
        private final int boardId;
        private final int postId;
        private final int commentId;

        // Optional parameters
        private String content;

        public Builder(int boardId, int postId, int commentId) {
            this.boardId = boardId;
            this.postId = postId;
            this.commentId = commentId;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public CommentModifyDto build() {
            return new CommentModifyDto(this);
        }
    }

    private CommentModifyDto(Builder builder) {
        this.boardId = builder.boardId;
        this.postId = builder.postId;
        this.commentId = builder.commentId;
        this.content = builder.content;
    }
}
