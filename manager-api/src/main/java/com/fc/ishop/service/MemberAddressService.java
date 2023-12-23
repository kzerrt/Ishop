package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.MemberAddress;
import com.fc.ishop.vo.PageVo;

/**
 * @author florence
 * @date 2023/12/11
 */
public interface MemberAddressService extends IService<MemberAddress> {
    /**
     * 根据会员获取地址
     * @param memberId
     * @param pageVo
     * @return
     */
    IPage<MemberAddress> getAddressByMember(String memberId, PageVo pageVo);

    void deleteMemberAddress(String id);

    void updateMemberAddress(MemberAddress memberAddress);

    void saveMemberAddress(MemberAddress memberAddress);
}
