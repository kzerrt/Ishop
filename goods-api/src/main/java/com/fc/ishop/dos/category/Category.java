package com.fc.ishop.dos.category;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author florence
 * @date 2023/12/11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("li_category")
@AllArgsConstructor
@NoArgsConstructor
public class Category extends BaseEntity {
    private static final long serialVersionUID = 88546623121L;

    @NotEmpty(message = "分类名称不能为空")
    //@ApiModelProperty(value = "分类名称")
    private String name;

    //@ApiModelProperty(value = "父id, 根节点为0")
    private String parentId;

    //@ApiModelProperty(value = "层级, 从0开始")
    private Integer level;

    //@ApiModelProperty(value = "排序值")
    private BigDecimal sortOrder;

    //@ApiModelProperty(value = "佣金比例")
    private Double commissionRate;

    //@ApiModelProperty(value = "分类图标")
    private String image;

    //@ApiModelProperty(value = "是否支持频道")
    private Boolean supportChannel;

    public Category(String id, String createBy, Date createTime, String updateBy, Date updateTime, Boolean deleteFlag, String name, String parentId, Integer level, BigDecimal sortOrder, Double commissionRate, String image, Boolean supportChannel) {
        super(id, createBy, createTime, updateBy, updateTime, deleteFlag);
        this.name = name;
        this.parentId = parentId;
        this.level = level;
        this.sortOrder = sortOrder;
        this.commissionRate = commissionRate;
        this.image = image;
        this.supportChannel = supportChannel;
    }

}
