package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SpecificationVo;
import com.fc.ishop.web.manager.SpecificationManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * 管理端,商品规格接口
 * @author florence
 * @date 2023/12/15
 */
@RestController
@RequestMapping("/goods/spec")
public class SpecificationManagerController {

    @Autowired
    private SpecificationManagerClient specificationManagerClient;

    /**
     * 通过id获取商品规格
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public ResultMessage<Specification> get(@PathVariable String id) {
        return specificationManagerClient.get(id);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public List<Specification> getAll() {
        return specificationManagerClient.getAll();
    }


    @GetMapping(value = "/page")
    public ResultMessage<Page<SpecificationVo>> getByPage(@RequestParam(required = false) String specName, PageVo pageVo) {
        return specificationManagerClient.getByPage(specName, pageVo);
    }

    @PutMapping
    public ResultMessage<Specification> update(@Valid SpecificationVo parameters) {
        return specificationManagerClient.update(parameters);
    }

    @PostMapping
    public ResultMessage<Specification> save(@Valid SpecificationVo parameters) {
        return specificationManagerClient.save(parameters);
    }

    @DeleteMapping(value = "/{ids}")
    public ResultMessage<Object> delAllByIds(@PathVariable List<String> ids) {
        return specificationManagerClient.delAllByIds(ids);
    }
}
