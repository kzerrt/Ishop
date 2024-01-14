package com.fc.ishop.dos.goods;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotEmpty;

/**
 * 商品计量单位
 * @author florence
 * @date 2023/12/13
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_goods_unit")
public class GoodsUnit extends BaseEntity {

    @NotEmpty(message = "计量单位名称不能为空")
    private String name;
}
