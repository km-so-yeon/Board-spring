package com.board.post.dto;

public class PostDto {

    private int boardId;

    private String title;

    private String content;

    public int getBoardId() { return this.boardId; };

    public static class Builder {
        // Required parameters
        private final int boardId;

        // Optional parameters
        private String title;
        private String content;

        public Builder(int boardId) {
            this.boardId = boardId;
        }

        public Builder title(String title) {
            this.title = title;
            return this;    // 이렇게 하면 체이닝해서 메서드를 호출할 수 있다.
        }

        public Builder content(String content) {
            this.content = content;
            return this;
        }

        public PostDto build() {
            return new PostDto(this);
        }
    }

    private PostDto(Builder builder) {
        boardId = builder.boardId;
        title   = builder.title;
        content = builder.content;
    }
}
