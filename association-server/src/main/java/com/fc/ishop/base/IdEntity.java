package com.fc.ishop.base;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * 数据库基础实体类
 *
 * @author Chopper
 * @version v1.0
 * @since 2020/8/20 14:34
 */

public abstract class IdEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    //@ApiModelProperty(value = "唯一标识", hidden = true)
    private String id;

    public IdEntity(String id) {
        this.id = id;
    }

    public IdEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
