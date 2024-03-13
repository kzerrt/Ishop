package com.fc.ishop.exception;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.ResultMessage;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 网关异常处理
 * @author florence
 * @date 2024/3/1
 */
@RestControllerAdvice
@Slf4j
public class ZuulExceptionHandler extends GlobalControllerExceptionHandler{
    @ExceptionHandler(ZuulException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ResultMessage<Object> handleServiceException(HttpServletRequest request, final Exception e, HttpServletResponse response) {

        log.error("网关异常[ZuulServiceException]:", e);

        //如果是自定义异常，则获取异常，返回自定义错误消息
        if (e instanceof ZuulException) {
            ZuulException z = ((ZuulException) e);
            if (z.errorCause != null) {
                return ResultUtil.error(z.nStatusCode, z.errorCause);
            }
        }

        //默认错误消息
        String errorMsg = "服务器异常，请稍后重试";
        if (e != null && e.getMessage() != null && e.getMessage().length() < MAX_LENGTH) {
            errorMsg = e.getMessage();
        }
        return ResultUtil.error(ResultCode.ERROR.code(), errorMsg);
    }
}
