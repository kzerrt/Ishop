package com.fc.ishop.controller.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Store;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.dto.StoreEditDto;
import com.fc.ishop.dto.StoreSearchParams;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.StoreDetailService;
import com.fc.ishop.service.StoreService;
import com.fc.ishop.vo.*;
import com.fc.ishop.vo.category.CategoryVo;
import com.fc.ishop.web.manager.StoreManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 店铺管理
 * @author florence
 * @date 2023/12/18
 */
@RestController
public class StoreController implements StoreManagerClient {
    @Autowired
    private StoreService storeService;
    @Autowired
    private StoreDetailService storeDetailService;

    @Override
    public ResultMessage<List<Store>> getALL() {
        return ResultUtil.data(storeService.list(new QueryWrapper<Store>().eq("store_disable", "OPEN")));
    }

    @Override
    public ResultMessage<Page<StoreVo>> getByPage(StoreSearchParams entity, PageVo page) {
        return ResultUtil.data(storeService.listPage(entity, page));
    }

    @Override
    public ResultMessage<StoreDetailVo> detail(String storeId) {
        return ResultUtil.data(storeDetailService.getStoreDetail(storeId));
    }

    @Override
    public ResultMessage<Store> add(AdminStoreApplyDto adminStoreApplyDTO) {
        return ResultUtil.data(storeService.add(adminStoreApplyDTO));
    }

    @Override
    public ResultMessage<Store> edit(String id, StoreEditDto storeEditDTO) {
        storeEditDTO.setStoreId(id);
        return ResultUtil.data(storeService.edit(storeEditDTO));
    }

    @Override
    public ResultMessage<Object> audit(String id, Integer passed) {
        storeService.audit(id, passed);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Store> disable(String id) {
        storeService.disable(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Store> enable(String id) {
        storeService.enable(id);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<List<CategoryVo>> firstCategory(String storeId) {
        return ResultUtil.data(storeDetailService.goodsManagementCategory(storeId));
    }

    @Override
    public ResultMessage<Store> getByMemberId(String memberId) {
        List<Store> list = storeService.list(new QueryWrapper<Store>().eq("member_id", memberId));
        if (list.size() > 0) {
            return ResultUtil.data(list.get(0));
        }
        return ResultUtil.data(null);
    }
}
