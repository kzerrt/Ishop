package com.fc.ishop.controller.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fc.ishop.dos.MemberAddress;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.MemberAddressService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 管理端 会员地址管理
 * @author florence
 * @date 2023/12/11
 */
@RestController
@RequestMapping("/member/address")
public class MemberAddressManagerController {
    @Autowired
    private MemberAddressService memberAddressService;
    /**
     * 会员地址查询
     */
    @GetMapping("/{memberId}")
    public ResultMessage<IPage<MemberAddress>> getByPage(@PathVariable String memberId, PageVo pageVo) {
        return ResultUtil.data(memberAddressService.getAddressByMember(memberId, pageVo));
    }

    /**
     * 根据id删除数据
     * @param id 表id
     * @return
     */
    @DeleteMapping("/delById/{id}")
    public ResultMessage<Object> delAddressById(@PathVariable String id) {
        memberAddressService.deleteMemberAddress(id);
        return ResultUtil.success();
    }

    /**
     * 编辑地址
     * @param memberAddress
     * @return
     */
    @PutMapping
    public ResultMessage<MemberAddress> editAddress(@Valid MemberAddress memberAddress) {
        memberAddressService.updateMemberAddress(memberAddress);
        return ResultUtil.data(memberAddress);
    }
    @PostMapping
    public ResultMessage<MemberAddress> addAddress(@Valid MemberAddress memberAddress) {
        memberAddressService.saveMemberAddress(memberAddress);
        return ResultUtil.data(memberAddress);
    }
}
