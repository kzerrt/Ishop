package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Member;
import com.fc.ishop.dto.MemberAddDto;
import com.fc.ishop.dto.MemberEditDto;
import com.fc.ishop.token.Token;
import com.fc.ishop.vo.MemberSearchVo;
import com.fc.ishop.vo.PageVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/1
 */
public interface MemberService extends IService<Member> {
    Member findByUsername(String username);
    Member findByPhone(String phone);
    /**
     * 分页查询会员列表
     * @param memberSearchVO
     * @param page
     * @return
     */
    IPage<Member> getMemberPage(MemberSearchVo memberSearchVO, PageVo page);

    /**
     * 添加会员
     * @param member
     * @return
     */
    Member saveMember(MemberAddDto member);

    /**
     *  修改会员信息
     * @param memberEditDto
     * @return
     */
    Member updateMember(MemberEditDto memberEditDto);

    /**
     * 修改会员状态
     * @param memberIds 会员id
     * @param disabled
     */
    boolean updateMemberStatus(List<String> memberIds, Boolean disabled);

    /**
     * 根据条件查询会员总数
     * @param memberSearchVo
     * @return
     */
    Integer getMemberNum(MemberSearchVo memberSearchVo);

    /**
     * 手机号，验证码登录
     * @param mobile
     * @return
     */
    Token mobilePhoneLogin(String mobile, String code, String uuid);

    /**
     * 获取用户信息
     * @return
     */
    Member getUserInfo();

}
