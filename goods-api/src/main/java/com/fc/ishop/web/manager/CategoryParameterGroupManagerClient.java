package com.fc.ishop.web.manager;

import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.vo.ParameterGroupVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * 管理端,分类绑定参数组接口
 * @author florence
 * @date 2023/12/12
 */
@FeignClient(value = "goods-server", contextId = "categoryParameter")
public interface CategoryParameterGroupManagerClient {

    /**
     * 查询某分类下参数信息
     * @param categoryId
     * @return
     */
    @GetMapping("/manager-cpg/get/{categoryId}")
    ResultMessage<List<ParameterGroupVo>> getCategoryParam(@PathVariable String categoryId);

    /**
     * 保存
     * @param categoryParameterGroup
     * @return
     */
    @PostMapping("/manager-cpg/save")
    ResultMessage<CategoryParameterGroup> saveCategoryParameterGroup(@RequestBody CategoryParameterGroup categoryParameterGroup);

    @PostMapping("/manager-cpg/update")
    ResultMessage<CategoryParameterGroup> update(@RequestBody CategoryParameterGroup categoryParameterGroup);
    @GetMapping("/manager-cpg/{id}")
    ResultMessage<Object> delAllByIds(@PathVariable String id);
}
