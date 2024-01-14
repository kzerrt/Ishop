package com.fc.ishop.controller;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.service.SmsService;
import com.fc.ishop.service.VerificationService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短信发送模块
 * @author florence
 * @date 2024/1/4
 */
@RestController
@RequestMapping("/sms")
public class SmsController {
    @Autowired
    private SmsService smsService;

    //(value = "发送短信验证码")
    @GetMapping("/{type}/{mobile}")
    public ResultMessage<Object> getSmsCode(
            @RequestHeader String uuid,
            @PathVariable String mobile,
            @PathVariable String type) {
        if (!smsService.sendSmsCode(mobile, type, uuid)) {
            throw new ServiceException(ResultCode.VERIFICATION_SMS_EXPIRED_ERROR);
        }
        return ResultUtil.success(ResultCode.VERIFICATION_SEND_SUCCESS);
    }
}
