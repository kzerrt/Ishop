package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Store;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.dto.StoreEditDto;
import com.fc.ishop.dto.StoreSearchParams;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.StoreVo;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface StoreService extends IService<Store> {
    /**
     * 分页条件查询
     * 用于展示店铺列表
     */
    Page<StoreVo> listPage(StoreSearchParams entity, PageVo page);

    Store add(AdminStoreApplyDto adminStoreApplyDTO);

    Store edit(StoreEditDto storeEditDTO);

    /**
     * 审核店铺申请
     * @param id
     * @param passed
     */
    boolean audit(String id, Integer passed);

    void disable(String id);
    void enable(String id);
}
