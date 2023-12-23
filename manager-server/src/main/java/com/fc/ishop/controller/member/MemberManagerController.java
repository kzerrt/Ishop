package com.fc.ishop.controller.member;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fc.ishop.dos.Member;
import com.fc.ishop.dto.MemberAddDto;
import com.fc.ishop.dto.MemberEditDto;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.MemberService;
import com.fc.ishop.vo.MemberSearchVo;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 管理员会员接口
 * @author florence
 * @date 2023/12/1
 */
@RestController
@RequestMapping("/member")
public class MemberManagerController {

    @Autowired
    private MemberService memberService;

    @GetMapping
    public ResultMessage<IPage<Member>> getByPage(MemberSearchVo memberSearchVo, PageVo pageVo) {
        return ResultUtil.data(memberService.getMemberPage(memberSearchVo, pageVo));
    }

    // 根据id查询会员信息
    @GetMapping(value = "/{id}")
    public ResultMessage<Member> get(@PathVariable String id) {
        return ResultUtil.data(memberService.getById(id));
    }
    // 添加会员
    @PostMapping
    public ResultMessage<Member> save(MemberAddDto member) {
        return ResultUtil.data(memberService.saveMember(member));
    }
    // 修改会员基本信息
    @PutMapping
    public ResultMessage<Member> update(@Valid MemberEditDto memberEditDto) {
        return ResultUtil.data(memberService.updateMember(memberEditDto));
    }
    // 修改会员状态，开启关闭会员
    @PutMapping("/updateMemberStatus")
    public ResultMessage<Object> updateMemberStatus(@RequestParam List<String> memberIds, @RequestParam Boolean disabled) {
        memberService.updateMemberStatus(memberIds, disabled);
        return ResultUtil.success();
    }
    // 根据条件查询会员总数
    @GetMapping("/num")
    public ResultMessage<Integer> getByPage(MemberSearchVo memberSearchVo) {
        return ResultUtil.data(memberService.getMemberNum(memberSearchVo));
    }
}
