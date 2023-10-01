package com.board.constant;

public enum BaseStatus {
    SUCCESS(true, 0, "요청에 성공하였습니다."),

    MEMBER_DUPLICATE_EMAIL(false, 1000, "이미 등록된 회원입니다."),
    MEMBER_DUPLICATE_IP(false, 1001, "이미 등록된 회원입니다."),
    MEMBER_NON_EXIST(false, 1002, "존재하지 않는 회원입니다."),
    MEMBER_PASSWORD_WORNG(false, 1003, "비밀번호가 틀립니다."),


    GUEST_IS_BLANK(false, 1004, "이메일 및 비밀번호를 입력해주세요."),

    BOARD_NOT_PERMISSION(false, 1004, "해당 게시판에 권한이 없습니다."),

    POST_NON_EXIST(false, 3000, "게시물이 존재하지 않습니다."),

    POST_CREATED_BY_NOT_MATCH(false, 3001, "게시물 작성자 정보와 일치하지 않습니다."),

    COMMENT_NON_EXIST(false, 4000, "댓글이 존재하지 않습니다."),

    COMMENT_CREATED_BY_NOT_MATCH(false, 4001, "댓글 작성자 정보와 일치하지 않습니다."),

    ;

    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

    public boolean isSuccess() { return this.isSuccess; }
    public int getCode() { return this.code; }
    public String getMessage() { return this.message; }
}
