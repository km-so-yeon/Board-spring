package com.board.member.dto;

public class MemberSignUpDto {

    private String email;

    private String password;

    private String nickname;

    private String ip;

    private int roleId;

    public String getEmail() { return this.email; }

    public void setIp(String ip) { this.ip = ip; }

    public static class Builder {
        // Required parameters
        private final String email;
        private final String password;
        private final String nickname;
        private final int roleId;

        // Optional parameters
        private String ip;

        public Builder(String email, String password, String nickname, int roleId) {
            this.email = email;
            this.password = password;
            this.nickname = nickname;
            this.roleId = roleId;
        }

        public Builder ip(String ip) {
            this.ip = ip;
            return this;
        }

        public MemberSignUpDto build() {
            return new MemberSignUpDto(this);
        }
    }

    public MemberSignUpDto() {

    }

    private MemberSignUpDto(Builder builder) {
        this.email = builder.email;
        this.password = builder.password;
        this.nickname = builder.nickname;
        this.roleId = builder.roleId;
        this.ip = builder.ip;
    }

}
