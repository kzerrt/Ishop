package com.fc.ishop.controller.store;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.Store;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.dto.StoreEditDto;
import com.fc.ishop.dto.StoreSearchParams;
import com.fc.ishop.vo.*;
import com.fc.ishop.vo.category.CategoryVo;
import com.fc.ishop.web.manager.StoreManagerClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/18
 */
@RestController
@RequestMapping("/store")
public class StoreManagerController {
    @Autowired
    private StoreManagerClient storeManagerClient;

    /**
     * 获取店铺分页列表
     */
    @GetMapping("/all")
    public ResultMessage<List<Store>> getALL() {
        return storeManagerClient.getALL();
    }

    /**
     *获取店铺分页列表
     */
    @GetMapping
    public ResultMessage<Page<StoreVo>> getByPage(StoreSearchParams entity, PageVo page) {
        Map<String, String> send = new HashMap<>();
        Gson gson = new Gson();
        send.put(SendParam.storeSearchParams, gson.toJson(entity));
        send.put(SendParam.pageVo, gson.toJson(page));
        return storeManagerClient.getByPage(send);
    }

    /**
     *获取店铺详情
     */
    @GetMapping(value = "/get/detail/{storeId}")
    public ResultMessage<StoreDetailVo> detail(@PathVariable String storeId) {
        return storeManagerClient.detail(storeId);
    }
    /**
     *添加店铺
     */
    @PostMapping(value = "/add")
    public ResultMessage<Store> add(@Valid AdminStoreApplyDto adminStoreApplyDTO) {
        return storeManagerClient.add(adminStoreApplyDTO);
    }
    /**
     *编辑店铺
     */
    @PutMapping(value = "/edit/{id}")
    public ResultMessage<Store> edit(@PathVariable String id, @Valid StoreEditDto storeEditDTO) {
        return storeManagerClient.edit(id, storeEditDTO);
    }
    /**
     *审核店铺申请
     */
    @PutMapping(value = "/audit/{id}/{passed}")
    public ResultMessage<Object> audit(@PathVariable String id, @PathVariable Integer passed) {
        return storeManagerClient.audit(id, passed);
    }
    /**
     *关闭店铺
     */
    @PutMapping(value = "/disable/{id}")
    public ResultMessage<Store> disable(@PathVariable String id) {
        return storeManagerClient.disable(id);
    }
    /**
     *开启店铺
     */
    @PutMapping(value = "/enable/{id}")
    public ResultMessage<Store> enable(@PathVariable String id) {
        return storeManagerClient.enable(id);
    }
    /**
     *查询一级分类列表
     */
    @GetMapping(value = "/ManagementCategory/{storeId}")
    public ResultMessage<List<CategoryVo>> firstCategory(String storeId) {
        return storeManagerClient.firstCategory(storeId);
    }

    /**
     *根据会员id查询店铺信息
     */
    @GetMapping("/{memberId}/member")
    public ResultMessage<Store> getByMemberId(@Valid @PathVariable String memberId) {
        return storeManagerClient.getByMemberId(memberId);
    }
}
