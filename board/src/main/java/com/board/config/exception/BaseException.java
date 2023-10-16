package com.board.config.exception;

import com.board.constant.BaseStatus;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException {

    private final BaseStatus status;

    public BaseException(BaseStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseStatus getStatus() {
        return this.status;
    }
}
