package com.fc.ishop.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.dto.SpecificationSearchParams;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SpecificationVo;
import com.fc.ishop.vo.goods.GoodsSpecValueVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface SpecificationService extends IService<Specification> {
    Specification getSpecification(String id);

    /**
     * 分页查询
     * @param searchParams
     * @param pageVo
     * @return
     */
    Page<SpecificationVo> getSpecificationPage(SpecificationSearchParams searchParams, PageVo pageVo);
    /**
     * 修改规格
     *
     * @param specificationVo 规格信息
     * @return 是否修改成功
     */
    boolean updateSpecification(SpecificationVo specificationVo);

    Specification addSpecification(SpecificationVo specificationVo);

    void deleteSpecification(List<String> ids);
    /**
     * 根据分类id获取规格信息
     *
     * @param categoryId 分类id
     * @return 商品规格值列表
     */
    List<GoodsSpecValueVo> getGoodsSpecValue(String categoryId);
}
