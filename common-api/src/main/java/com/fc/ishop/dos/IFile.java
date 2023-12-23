package com.fc.ishop.dos;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fc.ishop.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件类
 * @author florence
 * @date 2023/11/18
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("i_file")
public class IFile extends BaseEntity {
    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "存储文件名")
    private String name;

    //@ApiModelProperty(value = "存储文件名")
    //private String fileKey;

    //@ApiModelProperty(value = "大小")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long fileSize;

    //@ApiModelProperty(value = "文件类型")
    private String fileType;

    //@ApiModelProperty(value = "路径")
    private String url;

    //@ApiModelProperty(value = "拥有者id")
    private String ownerId;

    //@ApiModelProperty(value = "用户类型")
    private String userEnums;
}