package com.board.member.dto;

public class MemberSignUpDto {

    private String email;

    private String password;

    private String nickname;

    private String ip;

    private int roleId;

    public String getEmail() { return this.email; }

    public void setIp(String ip) { this.ip = ip; }
}
