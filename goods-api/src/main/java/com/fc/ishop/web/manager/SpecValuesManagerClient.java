package com.fc.ishop.web.manager;

import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 规格项管理
 * @author florence
 * @date 2023/12/16
 */
@FeignClient(value = "goods-server", contextId = "specValue")
public interface SpecValuesManagerClient {
    /**
     * 查询规格
     * @param id
     * @return
     */
    @GetMapping("/manager-sv/values/{id}")
    ResultMessage<List<SpecValues>> list(@PathVariable("id") String id);

    @PostMapping("/manager-sv/save/{specId}")
    ResultMessage<List<SpecValues>> saveSpecValue(@PathVariable String specId,
                                                  @NotNull(message = "至少添加一个规格值") @RequestParam String[] specValue);
}
