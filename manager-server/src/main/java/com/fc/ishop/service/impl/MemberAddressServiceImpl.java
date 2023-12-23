package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.MemberAddress;
import com.fc.ishop.mapper.MemberAddressMapper;
import com.fc.ishop.service.MemberAddressService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author florence
 * @date 2023/12/11
 */
@Service("memberAddressService")
@Transactional(rollbackFor = Exception.class)
public class MemberAddressServiceImpl
        extends ServiceImpl<MemberAddressMapper, MemberAddress> implements MemberAddressService {
    @Override
    public IPage<MemberAddress> getAddressByMember(String memberId, PageVo pageVo) {
        QueryWrapper<MemberAddress> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("member_id", memberId);
        return this.page(PageUtil.initPage(pageVo), queryWrapper);
    }

    @Override
    public void deleteMemberAddress(String id) {
        this.removeById(id);
    }

    /**
     * 修改会员默认地址
     * 会员地址有多个，只修改默认
     * @param memberAddress
     * @return
     */
    @Override
    public void updateMemberAddress(MemberAddress memberAddress) {
        //判断当前地址是否为默认地址，如果为默认需要将其他的地址修改为非默认
        updateDefaultAddress(memberAddress);

        this.update(memberAddress,
                new QueryWrapper<MemberAddress>()
                    .eq("id", memberAddress.getId()));
    }

    @Override
    public void saveMemberAddress(MemberAddress memberAddress) {
        // 判断是否为默认地址，如果为默认将其他地址改为非默认
        updateDefaultAddress(memberAddress);

        this.save(memberAddress);
    }

    /**
     * 修改会员默认地址
     * @param memberAddress 即将添加或修改的地址
     */
    private void updateDefaultAddress(MemberAddress memberAddress) {
        // 校验该地址是否是默认地址
        int count = this.count(new QueryWrapper<MemberAddress>().eq("member_id", memberAddress.getMemberId()));
        if (count == 1) {
            memberAddress.setIsDefault(true);
        }
        // 如果不是默认地址不处理
        if (!memberAddress.getIsDefault()) {
            return;
        }
        // 将会员地址修改为非默认地址
        LambdaUpdateWrapper<MemberAddress> lambdaUpdateWrapper = Wrappers.lambdaUpdate();
        lambdaUpdateWrapper.set(MemberAddress::getIsDefault, false);
        lambdaUpdateWrapper.eq(MemberAddress::getMemberId, memberAddress.getMemberId());
        this.update(lambdaUpdateWrapper);
    }
}
