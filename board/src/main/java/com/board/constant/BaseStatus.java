package com.board.constant;

public enum BaseStatus {
    SUCCESS(true, 0, "SUCCESS"),

    MEMBER_DUPLICATE_EMAIL(false, 1000, "MEMBER_DUPLICATE_EMAIL"),
    MEMBER_DUPLICATE_IP(false, 1001, "MEMBER_DUPLICATE_IP"),
    MEMBER_NOT_EXIST(false, 1002, "MEMBER_NOT_EXIST"),
    MEMBER_PASSWORD_WRONG(false, 1003, "MEMBER_PASSWORD_WRONG"),


    GUEST_IS_BLANK(false, 1004, "GUEST_IS_BLANK"),

    BOARD_NOT_PERMIT(false, 1004, "BOARD_NOT_PERMIT"),

    POST_NOT_EXIST(false, 3000, "POST_NOT_EXIST"),
    POST_CREATED_BY_NOT_MATCH(false, 3001, "POST_CREATED_BY_NOT_MATCH"),

    COMMENT_NOT_EXIST(false, 4000, "COMMENT_NOT_EXIST"),
    COMMENT_CREATED_BY_NOT_MATCH(false, 4001, "COMMENT_CREATED_BY_NOT_MATCH"),

    INTERNAL_SERVER_ERROR(false, 5000, "INTERNAL_SERVER_ERROR")

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
