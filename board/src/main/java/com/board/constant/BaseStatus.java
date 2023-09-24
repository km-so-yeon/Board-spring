package com.board.constant;

public enum BaseStatus {
    SUCCESS(true, 0, "요청에 성공하였습니다."),

    MEMBER_DUPLICATE_EMAIL(false, 1000, "이미 등록된 회원입니다."),
    MEMBER_NON_EXIST(false, 1001, "존재하지 않는 회원입니다."),

    MEMBER_PASSWORD_WORNG(false, 1002, "비밀번호가 틀립니다.")

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
