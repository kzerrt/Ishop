package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Region;
import com.fc.ishop.vo.RegionVo;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/11/29
 */
public interface RegionService extends IService<Region> {
    /**
     * 同步行政数据
     *
     * @param url
     */
    void synchronizationData(String url);


    List<Region> getItem(String id);

    /**
     * 获取地址
     *
     * @param cityCode 城市编码
     * @param townName 镇名称
     * @return
     */
    Map<String, Object> getRegion(String cityCode, String townName);

    /**
     * 获取所有的城市
     *
     * @return
     */
    List<RegionVo> getAllCity();
}
