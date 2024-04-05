package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Member;
import com.fc.ishop.dto.MemberAddDto;
import com.fc.ishop.dto.MemberEditDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.SwitchEnum;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.MemberMapper;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.MemberService;
import com.fc.ishop.token.ManagerTokenGenerate;
import com.fc.ishop.token.Token;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.utils.UuidUtils;
import com.fc.ishop.vo.MemberSearchVo;
import com.fc.ishop.vo.PageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/1
 */
@Service("memberService")
public class MemberServiceImpl
        extends ServiceImpl<MemberMapper, Member> implements MemberService {
    @Autowired
    private ManagerTokenGenerate managerTokenGenerate;
    @Override
    public Member findByUsername(String username) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Member findByPhone(String phone) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", phone);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Member> getMemberPage(MemberSearchVo memberSearchVO, PageVo page) {
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(memberSearchVO.getUsername()), "username", memberSearchVO.getUsername())
                .like(StringUtils.isNotEmpty(memberSearchVO.getNickName()), "nick_name", memberSearchVO.getNickName())
                .like(StringUtils.isNotEmpty(memberSearchVO.getMobile()), "mobile", memberSearchVO.getMobile());
        //按照会员状态查询
        queryWrapper.eq(StringUtils.isNotBlank(memberSearchVO.getDisabled()), "disabled",
                memberSearchVO.getDisabled().equals(SwitchEnum.OPEN.name()) ? 1 : 0);
        // 按创建日期排序
        queryWrapper.orderByDesc("create_time");

        return this.page(PageUtil.initPage(page), queryWrapper);
    }

    @Override
    public Member saveMember(MemberAddDto memberDto) {
        // 检测会员信息
        checkMember(memberDto.getUsername(), memberDto.getMobile());
        // 添加会员
        Member member = new Member(memberDto.getUsername(), memberDto.getPassword(), memberDto.getMobile());
        this.save(member);
        // todo 使用消息队列发送会员注册消息
        return member;
    }

    @Override
    public Member updateMember(MemberEditDto memberEditDto) {
        // 判断是否用户登录且会员id为当前登录id
        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        // 如果密码不为空加密
        if (StringUtils.isNotBlank(memberEditDto.getPassword())) {

        }
        Member member = this.findByUsername(memberEditDto.getUsername());
        // 传递修改会员信息
        BeanUtil.copyProperties(memberEditDto, memberEditDto);
        this.updateById(member);
        return member;
    }

    @Override
    public boolean updateMemberStatus(List<String> memberIds, Boolean disabled) {
        UpdateWrapper<Member> updateWrapper = Wrappers.update();
        updateWrapper.set("disabled", disabled);
        updateWrapper.in("id", memberIds);
        return this.update(updateWrapper);
    }

    @Override
    public Integer getMemberNum(MemberSearchVo memberSearchVo) {
        QueryWrapper<Member> queryWrapper = Wrappers.query();
        // 用户名查询
        queryWrapper.like(StringUtils.isNotBlank(memberSearchVo.getUsername()), "username", memberSearchVo.getUsername());
        // 电话号码查询
        queryWrapper.like(StringUtils.isNotBlank(memberSearchVo.getMobile()), "mobile", memberSearchVo.getMobile());
        // 状态查询
        queryWrapper.like(StringUtils.isNotBlank(memberSearchVo.getDisabled()), "disabled", memberSearchVo.getDisabled());
        queryWrapper.orderByDesc("create_time");
        return this.count(queryWrapper);
    }

    @Override
    public Token mobilePhoneLogin(String mobilePhone, String code, String uuid) {
        // 验证 验证码是否正确
        /*String sysCode = cache.get(CachePrefix.SMS_CODE.getPrefix() + "LOGIN" + uuid);
        if (StringUtils.isEmpty(sysCode)) {
            throw new ServiceException(ResultCode.VERIFICATION_SMS_EXPIRED_ERROR);
        }
        if (!sysCode.equals(code)) {
            throw new ServiceException(ResultCode.VERIFICATION_SMS_ERROR);
        }*/
        if (!"111111".equals(code)) {
            throw new ServiceException(ResultCode.VERIFICATION_SMS_ERROR);
        }
        QueryWrapper<Member> queryWrapper = new QueryWrapper();
        queryWrapper.eq("mobile", mobilePhone);
        Member member = baseMapper.selectOne(queryWrapper);
        //如果手机号不存在则自动注册用户
        if (member == null) {
            member = new Member(mobilePhone, UuidUtils.getUUID(), mobilePhone);
            //保存会员
            this.save(member);
            // todo 发送会员添加消息
            /*String destination = rocketmqCustomProperties.getMemberTopic() + ":" + MemberTagsEnum.MEMBER_REGISTER.name();
            rocketMQTemplate.asyncSend(destination, member, RocketmqSendCallbackBuilder.commonCallback());*/
        }
        member.setLastLoginDate(new Date());
        this.updateById(member);
        return managerTokenGenerate
                .createToken(new AuthUser(member.getUsername(), member.getId(),member.getNickName(), UserEnums.MEMBER),
                        false);
    }

    @Override
    public Member getUserInfo() {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        return this.findByUsername(tokenUser.getUsername());
    }

    private void checkMember(String username, String mobile) {
        // 判断用户名是否存在
        if (findByUsername(username) != null) {
            throw new ServiceException(ResultCode.USER_NAME_EXIST);
        }
        // 判断手机号是否存在
        if (findByPhone(mobile) != null) {
            throw new ServiceException(ResultCode.USER_PHONE_EXIST);
        }
    }
}
