package com.fc.ishop.vo;

import com.fc.ishop.dos.Parameters;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
@Data
public class ParameterGroupVo implements Serializable {
    private static final long serialVersionUID = 724427321881170297L;
    /**
     *参数组关联的参数集合
     */
    private List<Parameters> params;
    /**
     *参数组名称
     */
    private String groupName;
    /**
     *参数组id
     */
    private String groupId;
}
