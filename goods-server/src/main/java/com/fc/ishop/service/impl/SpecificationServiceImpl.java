package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.dos.category.CategorySpecification;
import com.fc.ishop.dto.SpecificationSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.SpecificationMapper;
import com.fc.ishop.service.CategorySpecificationService;
import com.fc.ishop.service.SpecValuesService;
import com.fc.ishop.service.SpecificationService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SpecificationVo;
import com.fc.ishop.vo.category.CategorySpecificationVo;
import com.fc.ishop.vo.goods.GoodsSpecValueVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 商品规格
 * @author florence
 * @date 2023/12/15
 */
@Service("specificationService")
public class SpecificationServiceImpl
        extends ServiceImpl<SpecificationMapper, Specification> implements SpecificationService {

    @Autowired
    private SpecValuesService specValuesService;
    @Autowired
    private CategorySpecificationService categorySpecificationService;

    @Override
    public Specification getSpecification(String id) {
        return baseMapper.selectById(id);
    }

    @Override
    public Page<SpecificationVo> getSpecificationPage(SpecificationSearchParams searchParams, PageVo pageVo) {
        Map<String, Object> param = new HashMap<>();
        param.put("specName", searchParams.getSpecName());
        List<SpecificationVo> specList = baseMapper.findSpecList(param);
        Page<SpecificationVo> page = new Page<>(pageVo.getPageNumber(), pageVo.getPageSize(), specList.size());
        page.setRecords(PageUtil.listToPage(pageVo, specList));
        return page;
    }

    @Override
    public boolean updateSpecification(SpecificationVo specificationVo) {
        checkExist(specificationVo.getId());
        return baseMapper.updateById(specificationVo) > 0;
    }

    @Override
    public Specification addSpecification(SpecificationVo specificationVo) {
        // 判断该规格是否存在
        Specification specification = this.getOne(new LambdaQueryWrapper<Specification>().eq(Specification::getSpecName, specificationVo.getSpecName()));
        if (specification == null) {
            this.save(specificationVo);
            specification = specificationVo;
        }
        // 获取分类规格项
        CategorySpecification categorySpecification = categorySpecificationService.getOne(
                new LambdaQueryWrapper<CategorySpecification>().eq(CategorySpecification::getSpecificationId, specification.getId()));
        if (categorySpecification == null) {
            categorySpecification = new CategorySpecification();
            categorySpecification.setSpecificationId(specification.getId());
            String categoryPath = specificationVo.getCategoryPath();
            if (!StringUtils.isEmpty(categoryPath)) {
                categorySpecification.setCategoryId(categoryPath.substring(categoryPath.lastIndexOf(",") + 1));
                categorySpecificationService.save(categorySpecification);
            }
        }
        if (!StringUtils.isEmpty(specificationVo.getSpecValue())) {
            specValuesService.saveSpecValue(specificationVo.getId(), new String[]{specificationVo.getSpecValue()});
        }
        return specification;
    }

    @Override
    public void deleteSpecification(List<String> ids) {
        // 判断是否有规格绑定分类
        QueryWrapper<CategorySpecification> queryWrapper = new QueryWrapper<>();
        List<String> toDel = new ArrayList<>();
        for (String id : ids) {
            queryWrapper.eq("specification_id", id);
            int count = categorySpecificationService.count(queryWrapper);
            if (count != 0) {
                baseMapper.deleteBatchIds(toDel);
                throw new ServiceException(ResultCode.SPEC_DELETE_ERROR);
            }
            toDel.add(id);
        }
        baseMapper.deleteBatchIds(toDel);
    }

    @Override
    public List<GoodsSpecValueVo> getGoodsSpecValue(String categoryId) {
        List<CategorySpecificationVo> vos = categorySpecificationService.getCategorySpecList(categoryId);
        List<GoodsSpecValueVo> res = new ArrayList<>();
        if (vos.isEmpty()) {
            return res;
        }
        // 获取规格id
        List<String> valueId = new ArrayList<>(vos.size());
        Map<String, String> map = new HashMap<>();

        for (CategorySpecificationVo vo : vos) {
            map.put(vo.getId(), vo.getName());
            valueId.add(vo.getId());
        }
        //使用valueId去查询规格值
        List<SpecValues> specValues = specValuesService.getSpecValues(valueId);
        // 循环规格
        map.forEach((specId, specName) -> {
            GoodsSpecValueVo vo = new GoodsSpecValueVo();
            vo.setName(specName);
            List<String> list = new LinkedList<>();
            for (SpecValues specValue : specValues) {
                if (specValue.getSpecId().equals(specId)) {
                    list.add(specValue.getSpecValue());
                }
            }
            vo.setValue(list);
            res.add(vo);
        });
        return res;
    }

    private Specification checkExist(String id) {
        Specification specification = this.getById(id);
        if (specification == null) {
            throw new ServiceException("当前商品已下架");
        }
        return specification;
    }
}
