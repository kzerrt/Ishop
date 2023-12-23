package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dto.BrandPageDto;
import com.fc.ishop.vo.BrandVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@FeignClient(value = "goods-server", contextId = "brand")
public interface BrandManagerClient {
    /**
     * 通过主键获取品牌
     */
    @GetMapping("/manager-br/get/{id}")
    ResultMessage<Brand> get(@PathVariable String id);

    /**
     * 获取所有可用品牌
     */
    @GetMapping("/manager-br/all")
    List<Brand> getAll();

    /**
     * 分页多条件查询
     * @param page
     * @return
     */
    @PostMapping("/manager-br/getByPage")
    ResultMessage<Page<Brand>> getByPage(@RequestBody BrandPageDto page);

    /**
     * 添加品牌
     * @param brand
     * @return
     */
    @PostMapping("/manager-br/saveBrand")
    ResultMessage<BrandVo> save(@Valid BrandVo brand);

    /**
     * 更新数据
     * @param id
     * @param brand
     * @return
     */
    @PostMapping("/manager-br/update/{id}")
    ResultMessage<BrandVo> update(@PathVariable String id,@RequestBody BrandVo brand);

    /**
     * 根据id修改品牌状态
     * @param id
     * @param disable
     * @return
     */
    @PutMapping("/manager-br/disable/{id}")
    ResultMessage<Object> disable(@PathVariable String id, @RequestParam Boolean disable);

    @GetMapping("/manager-br/delByIds/{ids}")
    ResultMessage<Object> delAllByIds(@PathVariable List<String> ids);
}
