package com.board.config.exception;

import com.board.constant.BaseStatus;

/**
 * 존재하지 않는 데이터일 경우 호출하는 예외 클래스
 */
public class NotExistException extends BaseException{
    public NotExistException(BaseStatus status) {
        super(status);
    }
}
