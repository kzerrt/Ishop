package com.fc.ishop.vo;

import com.fc.ishop.utils.StringUtils;
import lombok.Data;

import java.io.Serializable;

/**
 * @author florence
 * @date 2023/11/17
 */
@Data
public class PageVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "页号")
    private Integer pageNumber = 1;

    //@ApiModelProperty(value = "页面大小")
    private Integer pageSize = 10;

    //@ApiModelProperty(value = "排序字段")
    private String sort;

    //@ApiModelProperty(value = "排序方式 asc/desc")
    private String order;

    //@ApiModelProperty(value = "需要驼峰转换蛇形", notes = "一般不做处理，如果数据库中就是蛇形，则这块需要处理。")
    private Boolean notConvert;
    public PageVo(Integer pageNumber, Integer pageSize, String order) {
        this(pageNumber, pageSize, null, order, null);
    }

    public PageVo(Integer pageNumber, Integer pageSize, String sort, String order, Boolean notConvert) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
        this.order = order;
        this.notConvert = notConvert;
    }

    public PageVo() {
    }

    public Integer getMongoPageNumber() {
        int i = pageNumber - 1;
        if (i < 0) {
            return pageNumber;
        } else {
            return i;
        }
    }

    public String getSort() {
        if (!StringUtils.isEmpty(sort)) {
            if (notConvert == null || Boolean.FALSE.equals(notConvert)) {
                return StringUtils.camel2Underline(sort);
            } else {
                return sort;
            }
        }
        return sort;
    }
}
