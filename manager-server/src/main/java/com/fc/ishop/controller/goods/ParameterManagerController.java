package com.fc.ishop.controller.goods;

import com.fc.ishop.dos.Parameters;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.web.manager.ParameterManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 商品参数管理接口
 * @author florence
 * @date 2023/12/13
 */
@RestController
@RequestMapping("/goods/parameters")
public class ParameterManagerController {
    @Autowired
    private ParameterManagerClient parameterManagerClient;

    /**
     * 添加参数
     * @param parameters
     * @return
     */
    @PostMapping
    public ResultMessage<Parameters> save(@Valid Parameters parameters) {
        return parameterManagerClient.save(parameters);
    }

    /**
     * 编辑参数
     * @param parameters
     * @return
     */
    @PutMapping
    public ResultMessage<Parameters> update(@Valid Parameters parameters) {
        return parameterManagerClient.update(parameters);
    }

    /**
     * 通过id删除参数
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public ResultMessage<Object> delById(@PathVariable String id) {
        return parameterManagerClient.delById(id);
    }
}
