package com.fc.ishop.vo;

import com.fc.ishop.dos.Region;
import com.fc.ishop.utils.BeanUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author florence
 * @date 2023/11/29
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class RegionVo extends Region {
    /**
     * 子信息
     */
    private List<RegionVo> children;

    public RegionVo(Region region) {
        BeanUtil.copyProperties(region, this);
        this.children = new ArrayList<>();
    }
}
