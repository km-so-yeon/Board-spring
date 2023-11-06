package com.board.comment.dto;

public class CommentAddDto {

    private int boardId;

    private int postId;

    private String content;

    public int getBoardId() { return this.boardId; }

    public int getPostId() { return this.postId; }

    public String getContent() { return this.content; }

    public static class Builder {
        // Required parameters
        private final int boardId;
        private final int postId;

        // Optional parameters
        private String content;

        public Builder(int boardId, int postId) {
            this.boardId = boardId;
            this.postId = postId;
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public CommentAddDto build() {
            return new CommentAddDto(this);
        }
    }

    private CommentAddDto(Builder builder) {
        this.boardId = builder.boardId;
        this.postId = builder.postId;
        this.content = builder.content;
    }
}
