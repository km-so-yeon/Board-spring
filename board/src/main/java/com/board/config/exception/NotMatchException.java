package com.board.config.exception;

import com.board.constant.BaseStatus;

public class NotMatchException extends BaseException{
    public NotMatchException(BaseStatus status) {
        super(status);
    }
}
