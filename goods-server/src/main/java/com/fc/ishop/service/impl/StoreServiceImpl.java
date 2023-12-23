package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Member;
import com.fc.ishop.dos.Store;
import com.fc.ishop.dos.StoreDetail;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.dto.StoreEditDto;
import com.fc.ishop.dto.StoreSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.StoreStatusEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.StoreMapper;
import com.fc.ishop.service.GoodsService;
import com.fc.ishop.service.StoreDetailService;
import com.fc.ishop.service.StoreService;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.StoreVo;
import com.fc.ishop.web.goods.GoodsMemberClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2023/12/18
 */
@Service("storeService")
public class StoreServiceImpl
        extends ServiceImpl<StoreMapper, Store> implements StoreService {

    @Autowired
    private GoodsMemberClient goodsMemberClient;
    @Autowired
    private StoreDetailService storeDetailService;
    @Autowired
    private GoodsService goodsService;

    @Override
    public Page<StoreVo> listPage(StoreSearchParams entity, PageVo page) {
        return baseMapper.getStoreList(PageUtil.initPage(page), entity.queryWrapper());
    }

    @Override
    public Store add(AdminStoreApplyDto adminStoreApplyDTO) {
        // 判断店铺名是否存在
        QueryWrapper<Store> query = Wrappers.query();
        query.eq("store_name", adminStoreApplyDTO.getStoreName());
        if (baseMapper.selectOne(query) != null) {
            throw new ServiceException(ResultCode.STORE_NAME_EXIST_ERROR);
        }
        Member member = goodsMemberClient.getById(adminStoreApplyDTO.getMemberId());
        //判断用户是否存在
        if (member == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        //判断是否拥有店铺
        if (member.getHaveStore()) {
            throw new ServiceException(ResultCode.STORE_APPLY_DOUBLE_ERROR);
        }

        //添加店铺
        Store store = new Store(member, adminStoreApplyDTO);
        this.save(store);
        //判断是否存在店铺详情，如果没有则进行新建，如果存在则进行修改
        StoreDetail storeDetail = new StoreDetail(store, adminStoreApplyDTO);

        storeDetailService.save(storeDetail);

        LambdaUpdateWrapper<Member> updateWrapper = Wrappers.lambdaUpdate();

        updateWrapper.eq(Member::getId, member.getId())
                .set(Member::getHaveStore, true)
                .set(Member::getStoreId, store.getId());

        goodsMemberClient.updateMember(updateWrapper);
        return store;
    }

    @Override
    public Store edit(StoreEditDto storeEditDTO) {
        if (storeEditDTO == null) {
            throw new ServiceException(ResultCode.STORE_NOT_EXIST);
        }
        // 判断店铺名是否唯一
        Store tmp = baseMapper.selectOne(new QueryWrapper<Store>().eq("store_name", storeEditDTO.getStoreName()));
        if (tmp != null && !storeEditDTO.getStoreId().equals(tmp.getId())) {
            throw new ServiceException(ResultCode.STORE_NAME_EXIST_ERROR);
        }
        // 修改店铺详情页
        updateStoreDetail(storeEditDTO);
        // 修改店铺信息
        return updateStore(storeEditDTO);
    }

    @Override
    public boolean audit(String id, Integer passed) {
        Store store = baseMapper.selectById(id);
        if (store == null) {
            throw new ServiceException(ResultCode.STORE_NOT_EXIST);
        }
        if (passed == 0) {
            store.setStoreDisable(StoreStatusEnum.OPEN.value());
            // todo 添加店铺页面
            // 修改会员表示已有店铺
            Member member = goodsMemberClient.getById(store.getMemberId());
            // 可能会员已存在店铺
            if (member.getHaveStore() == null || !member.getHaveStore()) {
                member.setHaveStore(true);
            }
            member.setStoreId(id);
        } else {
            store.setStoreDisable(StoreStatusEnum.REFUSED.value());
        }
        return updateById(store);
    }

    @Override
    public void disable(String id) {
        changStatus(id, false);
    }

    @Override
    public void enable(String id) {
        changStatus(id, true);
    }

    /**
     * 修改店铺状态
     * @param id 店铺id
     * @param open true 为开启
     * @return
     */
    private boolean changStatus(String id, boolean open) {
        Store store = baseMapper.selectById(id);
        if (store == null) {
            throw new ServiceException(ResultCode.STORE_NOT_EXIST);
        }
        if (open) {
            store.setStoreDisable(StoreStatusEnum.OPEN.value());
        } else {
            store.setStoreDisable(StoreStatusEnum.CLOSED.value());

            //下架所有此店铺商品
            goodsService.underStoreGoods(id);
        }
        return baseMapper.updateById(store) > 0;
    }

    private Store updateStore(StoreEditDto storeEditDTO) {
        Store store = baseMapper.selectById(storeEditDTO.getStoreId());
        if (store != null) {
            BeanUtil.copyProperties(storeEditDTO, store);
            store.setId(storeEditDTO.getStoreId());
            this.updateById(store);
        }
        return store;
    }

    private void updateStoreDetail(StoreEditDto storeEditDTO) {
        StoreDetail storeDetail = new StoreDetail();
        BeanUtil.copyProperties(storeEditDTO, storeDetail);
        storeDetailService.update(storeDetail, new QueryWrapper<StoreDetail>().eq("store_id", storeEditDTO.getStoreId()));
    }
}
