package com.fc.ishop.controller;

import com.fc.ishop.dos.Parameters;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.service.ParametersService;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.ParameterManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 参数管理接口
 * @author florence
 * @date 2023/12/13
 */
@RestController
public class ParameterController implements ParameterManagerClient {
    @Autowired
    private ParametersService parametersService;

    @Override
    public ResultMessage<Parameters> save(@Valid Parameters parameters) {
        try {
            parametersService.save(parameters);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PARAMETER_SAVE_ERROR);
        }
        return ResultUtil.data(parameters);
    }

    @Override
    public ResultMessage<Parameters> update(@Valid Parameters parameters) {
        try {
            parametersService.updateById(parameters);
        } catch (Exception e) {
            throw new ServiceException(ResultCode.PARAMETER_UPDATE_ERROR);
        }
        return ResultUtil.data(parameters);
    }

    @Override
    public ResultMessage<Object> delById(String id) {
        parametersService.removeById(id);
        return ResultUtil.success();
    }
}
