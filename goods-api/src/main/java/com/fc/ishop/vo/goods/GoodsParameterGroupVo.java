package com.fc.ishop.vo.goods;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品参数 集合
 * @author florence
 * @date 2023/12/12
 */
@Data
public class GoodsParameterGroupVo implements Serializable {
    private static final long serialVersionUID = 1450550797436233753L;
    /**
     *参数组关联的参数集合
     */
    private List<GoodsParamsVo> params;
    /**
     *参数组名称
     */
    private String groupName;
    /**
     *参数组id
     */
    private String groupId;
}
