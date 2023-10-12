package com.board.config.exception;

import com.board.constant.BaseStatus;

/**
 * 데이터 입력 시 중복일 경우 호출하는 예외 클래스
 */
public class DuplicateDataException extends BaseException{
    public DuplicateDataException(BaseStatus status) {
        super(status);
    }
}
