package com.fc.ishop.web.goods;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fc.ishop.dos.Member;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author florence
 * @date 2023/12/18
 */
@FeignClient("manager-server")
public interface GoodsMemberClient {
    // 通过id获取成员
    @GetMapping("/goods-member/member/get/{id}")
    Member getById(@PathVariable String id);
    @PostMapping("goods-member/update")
    void updateMember(@RequestBody LambdaUpdateWrapper<Member> updateWrapper);

    @PostMapping("/goods-member/update")
    void update(@RequestBody Member member);
}
