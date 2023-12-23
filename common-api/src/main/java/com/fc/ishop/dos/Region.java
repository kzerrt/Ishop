package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地区
 * @author florence
 * @date 2023/11/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_region")
public class Region extends BaseEntity {
    private static final long serialVersionUID = 418341656517240988L;

    //@ApiModelProperty(value = "父id")
    private String parentId;

    //@ApiModelProperty(value = "区域编码")
    private String adCode;

    //@ApiModelProperty(value = "城市代码")
    private String cityCode;

    //@ApiModelProperty(value = "区域中心点经纬度")
    private String center;

    /*@ApiModelProperty(value =
            "行政区划级别" +
                    "country:国家" +
                    "province:省份（直辖市会在province和city显示）" +
                    "city:市（直辖市会在province和city显示）" +
                    "district:区县" +
                    "street:街道")*/
    private String level;

    //@ApiModelProperty(value = "名称")
    private String name;

    //@ApiModelProperty(value = "行政地区路径，类似：1，2，3 ")
    private String path;

    //@ApiModelProperty(value = "排序")
    private Integer orderNum;
}
