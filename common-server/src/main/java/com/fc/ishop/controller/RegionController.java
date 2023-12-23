package com.fc.ishop.controller;

import com.fc.ishop.dos.Region;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.RegionService;
import com.fc.ishop.vo.RegionVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地址信息接口
 * @author florence
 * @date 2023/11/29
 */
@RestController
@RequestMapping("/region")
public class RegionController {
    @Autowired
    private RegionService regionService;

    /**
     * 获取地区信息
     * @param cityCode
     * @param townName
     * @return
     */
    @GetMapping("/region")
    public ResultMessage<Object> getRegion(@RequestParam String cityCode, @RequestParam String townName) {
        return ResultUtil.data(regionService.getRegion(cityCode, townName));
    }

    /**
     * 通过id获取子地区
     * @param id
     * @return
     */
    @GetMapping("/item/{id}")
    public ResultMessage<List<Region>> getItem(@PathVariable String id) {
        return ResultUtil.data(regionService.getItem(id));
    }
    @GetMapping("/allCity")
    public ResultMessage<List<RegionVo>> getAllCity() {
        return ResultUtil.data(regionService.getAllCity());
    }
}
