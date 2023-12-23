package com.fc.ishop.vo.goods;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

/**
 * 规格值
 * @author florence
 * @date 2023/12/16
 */
@Data
public class GoodsSpecValueVo {

    /**
     * 规格值名字
     */
    @TableField(value = "name")
    private String name;

    /**
     * 规格值名字
     */
    @TableField(value = "value")
    private List<String> value;
}
