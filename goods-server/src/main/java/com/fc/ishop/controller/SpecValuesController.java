package com.fc.ishop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.SpecValuesService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.SpecValuesManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/16
 */
@RestController
public class SpecValuesController implements SpecValuesManagerClient {

    @Autowired
    private SpecValuesService specValuesService;

    @Override
    public ResultMessage<List<SpecValues>> list(String id) {
        return ResultUtil.data(specValuesService.query().eq("spec_id", id).list());
    }

    @Override
    public ResultMessage<List<SpecValues>> saveSpecValue(String specId, @NotNull(message = "至少添加一个规格值") String[] specValue) {
        //重新添加
        List<SpecValues> list = specValuesService.saveSpecValue(specId, specValue);
        return ResultUtil.data(list);
    }

    @Override
    public Page<SpecValues> queryByParams(String id, String specVal, PageVo pageVo) {
        return specValuesService.queryByParams(id, specVal, pageVo);
    }
}
