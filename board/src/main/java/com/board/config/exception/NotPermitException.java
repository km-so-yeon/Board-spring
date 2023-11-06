package com.board.config.exception;

import com.board.constant.BaseStatus;

/**
 * 게시판에 권한을 갖지 않을 경우 호출하는 예외 클래스
 */
public class NotPermitException extends BaseException{
    public NotPermitException(BaseStatus status) {
        super(status);
    }
}
