package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.StoreDetail;
import com.fc.ishop.vo.StoreDetailVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface StoreDetailService extends IService<StoreDetail> {
    /**
     * 根据店铺id查询详情
     * @param storeId
     * @return
     */
    StoreDetailVo getStoreDetail(String storeId);

    /**
     * 获取一级分类
     * @param storeId
     * @return
     */
    List goodsManagementCategory(String storeId);
}
