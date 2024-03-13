package com.fc.ishop.exception;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 全局异常异常处理
 *
 * @author Chopper
 */
@RestControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

    /**
     * 如果超过长度，则前后段交互体验不佳，使用默认错误消息
     */
    static Integer MAX_LENGTH = 200;

    /**
     * 自定义异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultMessage<Object> handleServiceException(HttpServletRequest request, final Exception e, HttpServletResponse response) {

        log.error("全局异常[ServiceException]:", e);

        //如果是自定义异常，则获取异常，返回自定义错误消息
        if (e instanceof ServiceException) {
            ResultCode resultCode = ((ServiceException) e).getResultCode();
            if (resultCode != null) {
                return ResultUtil.error(resultCode.code(), resultCode.message());
            }
        }

        //默认错误消息
        String errorMsg = "服务器异常，请稍后重试";
        if (e != null && e.getMessage() != null && e.getMessage().length() < MAX_LENGTH) {
            errorMsg = e.getMessage();
        }
        return ResultUtil.error(ResultCode.ERROR.code(), errorMsg);
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultMessage<Object> runtimeExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

        log.error("全局异常[RuntimeException]:", e);

        return ResultUtil.error(ResultCode.ERROR.code(), e.getMessage());
    }


    /**
     * bean校验未通过异常
     *
     * @see org.springframework.validation.Validator
     * @see org.springframework.validation.DataBinder
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResultMessage<Object> validExceptionHandler(HttpServletRequest request, final Exception e, HttpServletResponse response) {

        BindException exception = (BindException) e;
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            return ResultUtil.error(ResultCode.PARAMS_ERROR.code(), error.getDefaultMessage());
        }
        return ResultUtil.error(ResultCode.PARAMS_ERROR);
    }

}
