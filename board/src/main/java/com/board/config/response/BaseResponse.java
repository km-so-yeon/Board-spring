package com.board.config.response;

import com.board.constant.BaseStatus;
import static com.board.constant.BaseStatus.SUCCESS;


public class BaseResponse<T> {

    private final Boolean isSuccess;
    private final int code;
    private final String message;
    private T result;

    // 요청 성공했을 때
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();
        this.result = result;
    }

    // 요청 실패했을 때
    public BaseResponse(BaseStatus status) {
        this.isSuccess = status.isSuccess();
        this.code = status.getCode();
        this.message = status.getMessage();
    }

}
