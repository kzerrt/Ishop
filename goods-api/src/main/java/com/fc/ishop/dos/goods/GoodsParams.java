package com.fc.ishop.dos.goods;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * 商品关联参数
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_goods_params")
public class GoodsParams extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableField(value = "goods_id")
    private String goodsId;
    /**
     * 参数id
     */
    @TableField(value = "param_id")
    private String paramId;
    /**
     * 参数名字
     */
    @TableField(value = "param_name")
    private String paramName;
    /**
     * 参数值
     */
    @TableField(value = "param_value")
    @Length(max = 100, message = "参数值字符不能大于120")
    private String paramValue;
}
