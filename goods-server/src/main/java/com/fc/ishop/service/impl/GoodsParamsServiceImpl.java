package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.dos.goods.GoodsParams;
import com.fc.ishop.mapper.GoodsParamsMapper;
import com.fc.ishop.service.CategoryParamsGroupService;
import com.fc.ishop.service.GoodsParamsService;
import com.fc.ishop.vo.goods.GoodsParameterGroupVo;
import com.fc.ishop.vo.goods.GoodsParamsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品参数关联类
 * @author florence
 * @date 2023/12/12
 */
@Service("goodsParamsService")
@Transactional
public class GoodsParamsServiceImpl
        extends ServiceImpl<GoodsParamsMapper, GoodsParams> implements GoodsParamsService {
    // 分类参数组
    @Autowired
    private CategoryParamsGroupService categoryParamsGroupService;


    @Override
    public List<GoodsParameterGroupVo> queryGoodsParams(String goodsId, String categoryId) {
        // 查询分类关联参数组
        List<CategoryParameterGroup> groupList = categoryParamsGroupService.getCategoryGroup(categoryId);
        //查询商品参数
        List<GoodsParamsVo> paramList = baseMapper.paramList(goodsId, categoryId);
        return convertParamList(groupList, paramList);
    }

    @Override
    public List<GoodsParams> getGoodsParamsByGoodsId(String goodsId) {
        return this.list(new LambdaQueryWrapper<GoodsParams>().eq(GoodsParams::getGoodsId, goodsId));
    }

    private List<GoodsParameterGroupVo> convertParamList(List<CategoryParameterGroup> groupList, List<GoodsParamsVo> paramList) {
        Map<String/*groupId*/, List<GoodsParamsVo>> map = new HashMap<>(16);
        // 将商品参数按照分类组分开
        for (GoodsParamsVo vo : paramList) {
            List<GoodsParamsVo> group = map.get(vo.getGroupId());
            if (group != null) {
                group.add(vo);
            } else {
                List<GoodsParamsVo> list = new ArrayList<>();
                list.add(vo);
                map.put(vo.getGroupId(), list);
            }
        }
        List<GoodsParameterGroupVo> resource = new ArrayList<>();
        for (CategoryParameterGroup group : groupList) {
            GoodsParameterGroupVo vo = new GoodsParameterGroupVo();
            vo.setGroupName(group.getGroupName());
            vo.setGroupId(group.getId());
            vo.setParams(map.get(group.getId()));
            resource.add(vo);
        }
        return resource;
    }

}
