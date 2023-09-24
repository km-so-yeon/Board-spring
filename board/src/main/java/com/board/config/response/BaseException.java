package com.board.config.response;

import com.board.constant.BaseStatus;

public class BaseException extends RuntimeException {

    private BaseStatus status;

    public BaseException(BaseStatus status) {
        this.status = status;
    }

    public BaseStatus getStatus() {
        return this.status;
    }
}
