package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.GoodsUnit;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.GoodsUnitManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/13
 */
@RestController
@RequestMapping("/goods/goodsUnit")
public class GoodsUnitManagerController {
    @Autowired
    private GoodsUnitManagerClient goodsUnitManagerClient;

    /**
     * 分页获取商品计量单位
     */
    @GetMapping
    public ResultMessage<Page<GoodsUnit>> getByPage(PageVo pageVO) {
        return goodsUnitManagerClient.getByPage(pageVO);
    }
    /**
     *获取商品计量单位
     */
    @GetMapping("/get/{id}")
    public ResultMessage<GoodsUnit> getById(@NotNull @PathVariable String id) {
        return goodsUnitManagerClient.getById(id);
    }
    /**
     *添加商品计量单位
     */
    @PostMapping
    public ResultMessage<GoodsUnit> save(@Valid GoodsUnit goodsUnit) {
        return goodsUnitManagerClient.save(goodsUnit);
    }
    /**
     *编辑商品计量单位
     */
    @PutMapping("/{id}")
    public ResultMessage<GoodsUnit> update(@NotNull @PathVariable String id, @Valid GoodsUnit goodsUnit) {
        return goodsUnitManagerClient.update(id, goodsUnit);
    }
    /**
     *删除商品计量单位
     */
    @DeleteMapping("/delete/{ids}")
    public ResultMessage<Object> delete(@NotNull @PathVariable List<String> ids) {
        return goodsUnitManagerClient.delete(ids);
    }

}
