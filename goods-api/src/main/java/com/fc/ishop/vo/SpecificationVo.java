package com.fc.ishop.vo;

import com.fc.ishop.dos.Specification;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class SpecificationVo extends Specification {
    private static final long serialVersionUID = 5504602856844228350L;

    /**
     * 规格项名称
     */
    private String specValue;

    /**
     * 分类path
     */
    private String categoryPath;

    public SpecificationVo(String specName, String storeId, String categoryPath) {

        this.setSpecName(specName);
        this.setStoreId(storeId);
        this.categoryPath = categoryPath;


    }
}
