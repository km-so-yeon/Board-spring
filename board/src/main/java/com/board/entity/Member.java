package com.board.entity;

import java.util.Date;

public class Member {

    private int memberId;

    private String email;

    private String password;

    private String nickname;

    private Date createdAt;

    private Date updatedAt;

    private Date leaveAt;

    private int roleId;

    public int getMemberId() { return this.memberId; }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getNickname() { return this.nickname; }

    public int getRoleId() { return this.roleId; }

    public void setRoleIdGuest() { this.roleId = 1; }

    public void clearPassword() { this.password = null; }
}
