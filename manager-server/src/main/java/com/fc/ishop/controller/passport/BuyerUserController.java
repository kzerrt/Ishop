package com.fc.ishop.controller.passport;

import com.fc.ishop.contant.BuyerPage;
import com.fc.ishop.dos.Member;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.MemberService;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

/**
 * 用户登录接口
 * @author florence
 * @date 2024/1/4
 */
@RestController
//(tags = "买家端,会员接口")
@RequestMapping("/buyer/members")
public class BuyerUserController {
    @Autowired
    private MemberService memberService;
    //(value = "短信登录接口")
    @PostMapping("/login")
    public ResultMessage<Object> smsLogin(@NotNull(message = "手机号为空") @RequestParam String mobile,
                                          @NotNull(message = "验证码为空") @RequestParam String code,
                                          @RequestHeader String uuid) {
        return ResultUtil.data(memberService.mobilePhoneLogin(mobile, code, uuid));
    }
    @GetMapping("/pageData")
    public ResultMessage<String> pageData() {
        return ResultUtil.data(BuyerPage.pageData);
    }

    //(value = "获取当前登录用户接口")
    @GetMapping
    public ResultMessage<Member> getUserInfo() {
        return ResultUtil.data(memberService.getUserInfo());
    }
}
