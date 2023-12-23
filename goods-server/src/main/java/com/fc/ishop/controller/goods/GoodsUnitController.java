package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.goods.GoodsUnit;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.GoodsUnitService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.GoodsUnitManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/13
 */
@RestController
public class GoodsUnitController implements GoodsUnitManagerClient {
    @Autowired
    private GoodsUnitService goodsUnitService;

    @Override
    public ResultMessage<Page<GoodsUnit>> getByPage(PageVo pageVo) {
        return ResultUtil.data(goodsUnitService.page(PageUtil.initPage(pageVo)));
    }

    @Override
    public ResultMessage<GoodsUnit> getById(@NotNull String id) {
        return ResultUtil.data(goodsUnitService.getById(id));
    }

    @Override
    public ResultMessage<GoodsUnit> save(@Valid GoodsUnit goodsUnit) {
        goodsUnitService.save(goodsUnit);
        return ResultUtil.data(goodsUnit);
    }

    @Override
    public ResultMessage<GoodsUnit> update(String id,@Valid GoodsUnit goodsUnit) {
        goodsUnit.setId(id);
        goodsUnitService.updateById(goodsUnit);
        return ResultUtil.data(goodsUnit);
    }

    @Override
    public ResultMessage<Object> delete(List<String> ids) {
        goodsUnitService.removeByIds(ids);
        return ResultUtil.success();
    }
}
