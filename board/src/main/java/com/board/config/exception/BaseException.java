package com.board.config.exception;

import com.board.constant.BaseStatus;

public class BaseException extends RuntimeException {

    private BaseStatus status;

    public BaseException(BaseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseStatus getStatus() {
        return this.status;
    }
}
