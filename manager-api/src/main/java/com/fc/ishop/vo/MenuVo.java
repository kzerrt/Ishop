package com.fc.ishop.vo;

import com.fc.ishop.dos.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class MenuVo extends Menu {
    //@ApiModelProperty(value = "子菜单")
    private List<MenuVo> children = new ArrayList<>();

    public MenuVo() {

    }
    public MenuVo(Menu menu) {
        BeanUtils.copyProperties(menu, this);
    }

    public List<MenuVo> getChildren() {
        if (children != null) {
            children.sort(Menu::compareTo);
            return children;
        }
        return null;
    }
}
