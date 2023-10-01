package com.board.entity;

import java.util.Date;

public class Board {

    private int boardId;

    private String boardNm;

    private Date createdAt;

    private String createdByNm;

    private Date updatedAt;

    private String updatedByNm;

    private int roleId;

    public int getBoardId() { return this.boardId; }

    public String getBoardNm() { return this.boardNm; }

    public Date getCreatedAt() { return this.createdAt; }

    public String getCreatedByNm() { return this.createdByNm; }

    public Date getUpdatedAt() { return this.updatedAt; }

    public String getUpdatedByNm() { return this.updatedByNm; }

    public int getRoleId() { return this.roleId; }
}
