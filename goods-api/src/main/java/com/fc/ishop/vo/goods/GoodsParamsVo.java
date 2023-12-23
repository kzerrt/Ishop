package com.fc.ishop.vo.goods;

import com.fc.ishop.dos.goods.GoodsParams;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author florence
 * @date 2023/12/12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsParamsVo extends GoodsParams {

    private static final long serialVersionUID = -4904700751774005326L;
    /**
     * 1 输入项   2 选择项
     */
    private Integer paramType;
    /**
     *选择项的内容获取值，使用optionList
     */
    private String options;
    /**
     *是否必填是  1    否   0
     */
    private Integer required;
    /**
     *参数组id
     */
    private String groupId;
    /**
     *是否可索引  1 可以   0不可以
     */
    private Integer isIndex;

    private String[] optionList;

    public void setOptionList(String[] optionList) {
        this.optionList = optionList;
    }

    public String[] getOptionList() {
        if (options != null) {
            return options.replaceAll("\r|\n", "").split(",");
        }
        return optionList;
    }
}
