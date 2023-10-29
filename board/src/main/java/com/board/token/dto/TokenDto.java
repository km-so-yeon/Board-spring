package com.board.token.dto;

import java.util.Date;

public class TokenDto {

    private int memberId;

    private String accessToken;

    private Date accessExpireAt;

    private String refreshToken;

    private Date refreshExpireAt;

    public int getMemberId() {
        return memberId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public Date getAccessExpireAt() {
        return accessExpireAt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public Date getRefreshExpireAt() {
        return refreshExpireAt;
    }

    public TokenDto(int memberId, String atk, long atkLiveTime, String rtk, long rtkLiveTime) {
        Date now = new Date();
        this.memberId = memberId;
        this.accessToken = atk;
        this.accessExpireAt = new Date(now.getTime() + atkLiveTime);
        this.refreshToken = rtk;
        this.refreshExpireAt = new Date(now.getTime() + rtkLiveTime);
    }
}
