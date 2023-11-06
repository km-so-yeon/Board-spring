package com.board.config.exception;

import com.board.constant.BaseStatus;

public class IsBlankException extends BaseException{
    public IsBlankException(BaseStatus status) {
        super(status);
    }
}
