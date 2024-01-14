package com.fc.ishop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.dto.SpecificationSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.SpecificationService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SpecificationVo;
import com.fc.ishop.web.manager.SpecificationManagerClient;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/15
 */
@RestController
public class SpecificationController implements SpecificationManagerClient {

    @Autowired
    private SpecificationService specificationService;

    @Override
    public ResultMessage<Specification> get(String id) {
        return ResultUtil.data(specificationService.getSpecification(id));
    }

    @Override
    public List<Specification> getAll() {
        return specificationService.list(new QueryWrapper<Specification>().eq("delete_flag", 0));
    }

    @Override
    public ResultMessage<Page<SpecificationVo>> getByPage(Map<String, String> send) {
        Gson gson = new Gson();
        SpecificationSearchParams searchParams = gson.fromJson(send.get(SendParam.specificationSearchParams), SpecificationSearchParams.class);
        PageVo pageVo = gson.fromJson(send.get(SendParam.pageVo), PageVo.class);
        return ResultUtil.data(specificationService.getSpecificationPage(searchParams, pageVo));
    }

    @Override
    public ResultMessage<Page<Specification>> getSpecification(Map<String, String> send) {
        Gson gson = new Gson();
        SpecificationSearchParams searchParams = gson.fromJson(send.get(SendParam.specificationSearchParams), SpecificationSearchParams.class);
        PageVo pageVo = gson.fromJson(send.get(SendParam.pageVo), PageVo.class);
        return ResultUtil.data(specificationService.getSpecification(searchParams, pageVo));
    }

    @Override
    public ResultMessage<Specification> update(SpecificationVo parameters) {
        if (parameters.getStoreId() == null) {
            parameters.setStoreId("0");
        }
        if (specificationService.updateSpecification(parameters)) {
            return ResultUtil.data(parameters);
        }
        throw new ServiceException(ResultCode.SPEC_UPDATE_ERROR);
    }

    @Override
    public ResultMessage<Specification> save(SpecificationVo parameters) {
        if (parameters.getStoreId() == null) {
            parameters.setStoreId(UserContext.getCurrentUser().getId());
        }
        if (specificationService.addSpecification(parameters) != null) {
            return ResultUtil.data(parameters);
        }
        throw new ServiceException(ResultCode.SPEC_SAVE_ERROR);
    }

    @Override
    public ResultMessage<Object> delAllByIds(List<String> ids) {
        specificationService.deleteSpecification(ids);
        return ResultUtil.success();
    }
}
