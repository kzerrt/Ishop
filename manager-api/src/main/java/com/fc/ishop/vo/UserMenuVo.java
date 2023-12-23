package com.fc.ishop.vo;

import com.fc.ishop.dos.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserMenuVo extends Menu {
    private static final long serialVersionUID = -7478870595109016162L;

    /**
     * 是否是超级管理员
     */
    private Boolean isSupper;
}
