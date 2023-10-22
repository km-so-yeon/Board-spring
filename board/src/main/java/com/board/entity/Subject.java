package com.board.entity;

/**
 * JWT 처리에서 사용
 */
public class Subject {

    private final int memberId;

    private final String email;

    private final String type;

    private Subject(int memberId, String email, String type) {
        this.memberId = memberId;
        this.email = email;
        this.type = type;
    }

    public static Subject atk(int memberId, String email) {
        return new Subject(memberId, email, "ATK");
    }

    public static Subject rtk(int memberId, String email) {
        return new Subject(memberId, email, "RTK");
    }

    public int getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public String getType() {
        return type;
    }
}
