package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Store;
import com.fc.ishop.dto.AdminStoreApplyDto;
import com.fc.ishop.dto.StoreEditDto;
import com.fc.ishop.dto.StoreSearchParams;
import com.fc.ishop.vo.*;
import com.fc.ishop.vo.category.CategoryVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 店铺管理
 * @author florence
 * @date 2023/12/18
 */
@FeignClient(value = "goods-server", contextId = "store")
public interface StoreManagerClient {
    /**
     * 获取所有店铺
     * @return
     */
    @GetMapping("/manager-st/getALl")
    ResultMessage<List<Store>> getALL();

    /**
     * 分页获取列表
     * @return
     */
    @PostMapping("/manager-st/page")
    ResultMessage<Page<StoreVo>> getByPage(@RequestBody Map<String, String> send);


    /**
     * 获取店铺详情
     * @param storeId
     * @return
     */
    @GetMapping("/manager-st/detail/{storeId}")
    ResultMessage<StoreDetailVo> detail(@PathVariable String storeId);

    /**
     * 添加店铺
     * @param adminStoreApplyDTO
     * @return
     */
    @PostMapping("/manager-st/add")
    ResultMessage<Store> add(@Valid @RequestBody AdminStoreApplyDto adminStoreApplyDTO);

    @PostMapping("/manager-st/edit/{id}")
    ResultMessage<Store> edit(@PathVariable String id, @RequestBody StoreEditDto storeEditDTO);

    /**
     * 审核店铺
     * @param id
     * @param passed 是否通过审核 0 通过 1 拒绝 编辑操作则不需传递
     * @return
     */
    @GetMapping("/manager-st/audit/{id}/{passed}")
    ResultMessage<Object> audit(@PathVariable String id, @PathVariable Integer passed);

    @GetMapping("/manager-st/disable/{id}")
    ResultMessage<Store> disable(@PathVariable String id);

    @GetMapping("/manager-st/enable/{id}")
    ResultMessage<Store> enable(@PathVariable String id);

    /**
     * 查询一级分类列表
     * @param storeId
     * @return
     */
    @GetMapping("/manager-st/ManagementCategory/{storeId}")
    ResultMessage<List<CategoryVo>> firstCategory(@PathVariable String storeId);

    /**
     * 根据会员id查询店铺信息
     * @param memberId
     * @return
     */
    @GetMapping("/manager-st/{memberId}/member")
    ResultMessage<Store> getByMemberId(@Valid @PathVariable String memberId);
}
