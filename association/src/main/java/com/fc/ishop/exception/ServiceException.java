package com.fc.ishop.exception;

import com.fc.ishop.enums.ResultCode;
import lombok.Data;

/**
 * @author Chopper
 */
@Data
public class ServiceException extends RuntimeException {

    private String msg;

    private ResultCode resultCode;

    public ServiceException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public ServiceException() {
        super("网络错误，请稍后重试！");
        this.msg = "网络错误，请稍后重试！";
    }

    public ServiceException(ResultCode resultCode) {
        this.resultCode = resultCode;
        this.msg = resultCode.message();
    }

    public ServiceException(ResultCode resultCode, String message) {
        this.resultCode = resultCode;
        this.msg = message;
    }

}
