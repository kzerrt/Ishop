package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.vo.PageVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface SpecValuesService extends IService<SpecValues> {
    List<SpecValues> saveSpecValue(String id, String[] values);

    /**
     * 根据id获取规格值
     * @param specIds 规格id集合
     * @return
     */
    List<SpecValues> getSpecValues(List<String> specIds);
    /**
     * 分页获取规格值
     *
     * @param specId  规格项ID
     * @param specVal 规格值
     * @param pageVo  分页参数
     * @return 规格值列表
     */
    Page<SpecValues> queryByParams(String specId, String specVal, PageVo pageVo);
    /**
     * 根据值获取规格值信息
     * 如果不存在则自动创建
     *
     * @param specValue 规格值
     * @param specId    规格ID
     * @return 规格值信息
     */
    SpecValues getSpecValues(String specValue, String specId);
}
