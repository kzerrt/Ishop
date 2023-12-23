package com.fc.ishop.controller.category;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.dos.category.CategoryParameterGroup;
import com.fc.ishop.dos.Parameters;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.service.CategoryParamsGroupService;
import com.fc.ishop.service.ParametersService;
import com.fc.ishop.vo.ParameterGroupVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.CategoryParameterGroupManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/13
 */
@RestController
public class CategoryParameterGroupController implements CategoryParameterGroupManagerClient {
    /**
     * 分类参数
     */
    @Autowired
    private CategoryParamsGroupService categoryParamsGroupService;
    /**
     * 参数组
     */
    @Autowired
    private ParametersService parametersService;

    @Override
    public ResultMessage<List<ParameterGroupVo>> getCategoryParam(String categoryId) {
        return ResultUtil.data(categoryParamsGroupService.getCategoryParams(categoryId));
    }

    @Override
    public ResultMessage<CategoryParameterGroup> saveCategoryParameterGroup(CategoryParameterGroup categoryParameterGroup) {
        try {
            categoryParamsGroupService.save(categoryParameterGroup);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.CATEGORY_PARAMETER_SAVE_ERROR);
        }
        return ResultUtil.data(categoryParameterGroup);
    }

    @Override
    public ResultMessage<CategoryParameterGroup> update(CategoryParameterGroup categoryParameterGroup) {
        try {
            categoryParamsGroupService.updateById(categoryParameterGroup);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.CATEGORY_PARAMETER_UPDATE_ERROR);
        }
        return ResultUtil.data(categoryParameterGroup);
    }

    @Override
    public ResultMessage<Object> delAllByIds(String id) {
        //删除参数
        parametersService.remove(new QueryWrapper<Parameters>().eq("group_id", id));
        //删除参数组
        categoryParamsGroupService.removeById(id);
        return ResultUtil.success();
    }
}
