package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Parameters;
import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.mapper.CategoryParamsGroupMapper;
import com.fc.ishop.service.CategoryParamsGroupService;
import com.fc.ishop.service.ParametersService;
import com.fc.ishop.vo.ParameterGroupVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/12
 */
@Service("categoryParamsGroupService")
public class CategoryParamsGroupServiceImpl
        extends ServiceImpl<CategoryParamsGroupMapper, CategoryParameterGroup> implements CategoryParamsGroupService {
    //商品参数
    @Autowired
    private ParametersService parametersService;

    @Override
    public List<CategoryParameterGroup> getCategoryGroup(String categoryId) {
        return this.list(new QueryWrapper<CategoryParameterGroup>().eq("category_id", categoryId));
    }

    @Override
    public List<ParameterGroupVo> getCategoryParams(String categoryId) {
        // 根据id查询数据
        List<CategoryParameterGroup> groups = this.getCategoryGroup(categoryId);
        //查询参数
        List<Parameters> params = parametersService.list(new QueryWrapper<Parameters>().eq("category_id", categoryId));

        //组合参数vo
        return convertParamList(groups, params);
    }

    /**
     * 拼装参数组和参数的返回值
     * @param groups 参数组
     * @param params 参数
     * @return
     */
    private List<ParameterGroupVo> convertParamList(List<CategoryParameterGroup> groups, List<Parameters> params) {
        Map<String, List<Parameters>> map = new HashMap<>(params.size());
        for (Parameters param : params) {
            List<Parameters> list = map.get(param.getGroupId());
            if (list == null) {
                list = new ArrayList<>();
                list.add(param);
                map.put(param.getGroupId(), list);
            } else {
                list.add(param);
            }
        }
        List<ParameterGroupVo> resource = new ArrayList<>();
        for (CategoryParameterGroup group : groups) {
            ParameterGroupVo vo = new ParameterGroupVo();
            vo.setGroupId(group.getId());
            vo.setGroupName(group.getGroupName());
            vo.setParams(map.get(group.getId()));
            resource.add(vo);
        }
        return resource;
    }
}
