package com.fc.ishop.web;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fc.ishop.dos.Member;
import com.fc.ishop.service.MemberService;
import com.fc.ishop.web.goods.GoodsMemberClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * 店铺用户信息查询
 * @author florence
 * @date 2023/12/18
 */
@RestController
public class GoodsMemberController implements GoodsMemberClient {
    @Autowired
    private MemberService memberService;
    @Override
    public Member getById(String id) {
        return memberService.getById(id);
    }

    @Override
    public void updateMember(LambdaUpdateWrapper<Member> updateWrapper) {
        memberService.update(updateWrapper);
    }

    @Override
    public void update(Member member) {
        memberService.updateById(member);
    }
}
