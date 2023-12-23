package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.GoodsUnit;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/13
 */
@FeignClient(value = "goods-server", contextId = "goodsUnit")
public interface GoodsUnitManagerClient {
    /**
     * 分页获取单位信息
     */
    @PostMapping("/manager-gu/list")
    ResultMessage<Page<GoodsUnit>> getByPage(PageVo pageVo);

    @GetMapping("/manager-gu/{id}")
    ResultMessage<GoodsUnit> getById(@PathVariable String id);

    @PostMapping("/manager-gu/save")
    ResultMessage<GoodsUnit> save(@RequestBody GoodsUnit goodsUnit);

    @PostMapping("/manager-gu/update/{id}")
    ResultMessage<GoodsUnit> update(@PathVariable String id,@RequestBody GoodsUnit goodsUnit);

    @GetMapping("/manager-gu/del/{ids}")
    ResultMessage<Object> delete(@PathVariable List<String> ids);
}
