package com.board.entity;

import com.board.member.dto.GuestDto;

import java.util.Date;

public class Member {

    private int memberId;

    private String email;

    private String password;

    private String nickname;

    private String ip;

    private Date createdAt;

    private Date updatedAt;

    private Date leaveAt;

    private int roleId;

    public int getMemberId() { return this.memberId; }

    public String getEmail() { return this.email; }

    public String getPassword() { return this.password; }

    public String getNickname() { return this.nickname; }

    public String getIp() { return this.ip; }

    public Date getCreatedAt() { return this.createdAt; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public Date getLeaveAt() { return this.leaveAt; }

    public int getRoleId() { return this.roleId; }

    public void clearPassword() { this.password = null; }

    public boolean isExist() {
        // 멤버Id가 0이면 존재하지 않는 사용자이다.
        if(this.memberId == 0) {
            return false;
        }
        return true;
    }

    public void setGuestDtl(GuestDto guestDto) {
        this.email = guestDto.getEmail();
        this.password = guestDto.getPassword();
    }
}
