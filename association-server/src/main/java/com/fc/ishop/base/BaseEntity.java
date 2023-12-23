package com.fc.ishop.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


/**
 * 数据库基础实体类
 *
 */
@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseEntity extends IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;


    //@CreatedBy
    @TableField(fill = FieldFill.INSERT)
    //@ApiModelProperty(value = "创建者", hidden = true)
    private String createBy;
    //@CreatedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    //@ApiModelProperty(value = "创建时间", hidden = true)
    private Date createTime;

    //@LastModifiedBy
    @TableField(fill = FieldFill.UPDATE)
    //@ApiModelProperty(value = "更新者", hidden = true)
    private String updateBy;

    //@LastModifiedDate
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE)
    //@ApiModelProperty(value = "更新时间", hidden = true)
    private Date updateTime;

    @TableField(fill = FieldFill.INSERT)
    //@ApiModelProperty(value = "删除标志 true/false 删除/未删除", hidden = true)
    private Boolean deleteFlag ;

    public BaseEntity(String id, String createBy, Date createTime, String updateBy, Date updateTime, Boolean deleteFlag) {
        super(id);
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.deleteFlag = deleteFlag;
    }
}
