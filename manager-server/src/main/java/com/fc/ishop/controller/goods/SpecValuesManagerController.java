package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.SpecValuesManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 规格值管理
 * @author florence
 * @date 2023/12/16
 */
@RestController
@RequestMapping("/goods/specValues")
public class SpecValuesManagerController {

    @Autowired
    private SpecValuesManagerClient specValuesManagerClient;
    @GetMapping(value = "/values/{id}")
    public ResultMessage<List<SpecValues>> list(@PathVariable("id") String id) {
        return specValuesManagerClient.list(id);
    }
    //(value = "查询规格值列表")
    @GetMapping(value = "/value/{id}")// store
    public ResultMessage<IPage<SpecValues>> list(@PathVariable("id") String id, String specVal, PageVo pageVo) {
        return ResultUtil.data(specValuesManagerClient.queryByParams(id, specVal, pageVo));
    }

    @PostMapping(value = "/save/{specId}")
    public ResultMessage<List<SpecValues>> saveSpecValue(@PathVariable String specId,
                                                         @NotNull(message = "至少添加一个规格值") @RequestParam String[] specValue) {
        return specValuesManagerClient.saveSpecValue(specId, specValue);
    }
}
