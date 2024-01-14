package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SpecificationVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 规格项管理
 * @author florence
 * @date 2023/12/15
 */
@FeignClient(value = "goods-server", contextId = "specification")
public interface SpecificationManagerClient {
    /**
     * 通过id获取商品规格
     * @param id
     * @return
     */
    @GetMapping("/manger-s/{id}")
    ResultMessage<Specification> get(@PathVariable String id);

    /**
     * 获取所有规格
     * @return
     */
    @GetMapping("/manager-s/all")
    List<Specification> getAll();

    /**
     * 分页获取
     */
    @PostMapping("/manager-s/page")
    ResultMessage<Page<SpecificationVo>> getByPage(@RequestBody Map<String, String> send);
    @PostMapping("/manager-s/pageStore")
    ResultMessage<Page<Specification>> getSpecification(Map<String, String> send);
    /**
     * 编辑规格
     */
    @PostMapping("/manager-s/update")
    ResultMessage<Specification> update(@RequestBody SpecificationVo parameters);

    @PostMapping("/manager-s/save")
    ResultMessage<Specification> save(@RequestBody SpecificationVo parameters);
    @GetMapping("/manager-s/{ids}")
    ResultMessage<Object> delAllByIds(@PathVariable List<String> ids);


}
