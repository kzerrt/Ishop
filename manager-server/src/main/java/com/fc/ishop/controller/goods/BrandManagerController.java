package com.fc.ishop.controller.goods;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.Brand;
import com.fc.ishop.dto.BrandPageDto;
import com.fc.ishop.vo.BrandVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.BrandManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 管理端 品牌管理
 * @author florence
 * @date 2023/12/11
 */
@RestController
@RequestMapping("/goods/brand")
public class BrandManagerController {
    @Autowired
    private BrandManagerClient brandManagerClient;

    @GetMapping(value = "/get/{id}")
    public ResultMessage<Brand> get(@NotNull @PathVariable String id) {
        return brandManagerClient.get(id);
    }

    @GetMapping(value = "/all")
    public List<Brand> getAll() {
        return brandManagerClient.getAll();
    }

    @GetMapping(value = "/getByPage")
    public ResultMessage<Page<Brand>> getByPage(BrandPageDto page) {
        return brandManagerClient.getByPage(page);
    }

    @PostMapping
    public ResultMessage<BrandVo> save(@Valid BrandVo brand) {
        return brandManagerClient.save(brand);
    }

    @PutMapping("/{id}")
    public ResultMessage<BrandVo> update(@PathVariable String id, @Valid BrandVo brand) {
        return brandManagerClient.update(id, brand);
    }

    @PutMapping(value = "/disable/{id}")
    public ResultMessage<Object> disable(@PathVariable String id, @RequestParam Boolean disable) {
        return brandManagerClient.disable(id, disable);
    }

    @DeleteMapping(value = "/delByIds/{ids}")
    public ResultMessage<Object> delAllByIds(@PathVariable List<String> ids) {
        return brandManagerClient.delAllByIds(ids);
    }
}
