package com.fc.ishop.web.manager;

import com.fc.ishop.dos.Parameters;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


/**
 * @author florence
 * @date 2023/12/13
 */
@FeignClient(value = "goods-server", contextId = "parameter")
public interface ParameterManagerClient {
    @PostMapping("/manager-p/save")
    ResultMessage<Parameters> save(Parameters parameters);
    @PostMapping("/manager-p/update")
    ResultMessage<Parameters> update( Parameters parameters);

    @GetMapping("/manager-p/{id}")
    ResultMessage<Object> delById(@PathVariable String id);
}
