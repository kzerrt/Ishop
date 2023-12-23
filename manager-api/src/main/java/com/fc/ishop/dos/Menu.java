package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_menu")
public class Menu
        extends BaseEntity implements Comparable<Menu> {

    private static final long serialVersionUID = 7050744476203495207L;

    //@ApiModelProperty(value = "菜单/权限名称")
    private String name;

    //@ApiModelProperty(value = "层级")
    private Integer level;

    //@ApiModelProperty(value = "菜单标题")
    private String title;

    //@ApiModelProperty(value = "赋权API地址,正则表达式")
    private String path;

    //@ApiModelProperty(value = "前端路由")
    private String frontRoute;

    //@ApiModelProperty(value = "图标")
    private String icon;

    //@ApiModelProperty(value = "父id")
    private String parentId = "0";

    //@ApiModelProperty(value = "说明备注")
    private String description;

    //@ApiModelProperty(value = "排序值")
    //@Column(precision = 10, scale = 2)
    private BigDecimal sortOrder;

    //@ApiModelProperty(value = "文件地址")
    private String frontComponent;

    @Override
    public int compareTo(Menu o) {
        if (o == null) return 0;
        return sortOrder.compareTo(o.sortOrder);
    }
}
