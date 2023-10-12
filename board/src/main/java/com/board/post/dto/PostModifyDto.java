package com.board.post.dto;

public class PostModifyDto {

    private int boardId;

    private int postId;

    private String title;

    private String content;

    public int getBoardId() { return this.boardId; }

    public int getPostId() { return this.postId; }

    public static class Builder{
        // Required parameters
        private final int boardId;
        private final int postId;

        // Optional parameters
        private String title;
        private String content;

        public Builder(int boardId, int postId) {
            this.boardId = boardId;
            this.postId = postId;
        }

        public Builder title(String title) {
            this.title = title;
            return this; // 이렇게 하면 체이닝해서 메서드를 호출할 수 있다.
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public PostModifyDto build() {
            return new PostModifyDto(this);
        }
    }

    private PostModifyDto(Builder builder) {
        this.boardId = builder.boardId;
        this.postId = builder.postId;
        this.title = builder.title;
        this.content = builder.content;
    }
}
