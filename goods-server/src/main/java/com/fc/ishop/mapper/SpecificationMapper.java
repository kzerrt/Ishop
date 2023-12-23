package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.vo.SpecificationVo;

import java.util.List;
import java.util.Map;

/**
 * @author florence
 * @date 2023/12/15
 */
public interface SpecificationMapper extends BaseMapper<Specification> {
    /**
     * 查询规格信息列表
     * @param param
     * @return
     */
    List<SpecificationVo> findSpecList(Map<String,Object> param);
}
