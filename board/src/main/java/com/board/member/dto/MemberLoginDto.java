package com.board.member.dto;

public class MemberLoginDto {

    private String email;

    private String password;

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public MemberLoginDto() {

    }

    public MemberLoginDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

}
