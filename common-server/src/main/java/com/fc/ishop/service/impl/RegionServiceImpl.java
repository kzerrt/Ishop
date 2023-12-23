package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.Region;
import com.fc.ishop.mapper.RegionMapper;
import com.fc.ishop.service.RegionService;
import com.fc.ishop.vo.RegionVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author florence
 * @date 2023/11/29
 */
@Service("regionService")
@Transactional
public class RegionServiceImpl
        extends ServiceImpl<RegionMapper, Region> implements RegionService {
    @Override
    public void synchronizationData(String url) {

    }

    @Override
    public List<Region> getItem(String id) {
        LambdaQueryWrapper<Region> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Region::getParentId, id);
        List<Region> regions = this.list(queryWrapper);
        // 对地区进行排序
        regions.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        return regions;
    }

    @Override
    public Map<String, Object> getRegion(String cityCode, String townName) {
        // 获取地址信息
        Region region = this.baseMapper.selectOne(new QueryWrapper<Region>()
                .eq("city_code", cityCode)
                .eq("name", townName));
        if (region != null) {
            // 获取层级关系
            String[] result = region.getPath().split(",");
            // 存放原因，有两个无用数据
            StringBuilder regionIds = new StringBuilder();// 地址id
            StringBuilder regionNames = new StringBuilder();// 地址名称
            for (int i = 2; i < result.length; i++) {
                // 根据path中id获取地区
                Region reg = this.baseMapper.selectById(result[i]);
                if (reg != null) {
                    regionIds.append(result[i]).append(",");
                    regionNames.append(reg.getName()).append(",");
                }
            }
            regionIds.append(region.getId());
            regionNames.append(region.getName());
            // 构建返回数据
            Map<String, Object> obj = new HashMap<>();
            obj.put("id", regionIds);
            obj.put("name", regionNames);
            return obj;
        }
        return null;
    }

    @Override
    public List<RegionVo> getAllCity() {
        LambdaQueryWrapper<Region> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        // 查询所有省市
        lambdaQueryWrapper.in(Region::getLevel, "city", "province");
        return regionTree(this.list(lambdaQueryWrapper));
    }

    private List<RegionVo> regionTree(List<Region> regions) {
        List<RegionVo> regionVos = new ArrayList<>();
        Map<String, RegionVo> provinces = new HashMap<>();
        // 将省份添加
        RegionVo regionVo;
        for (Region region : regions) {
            if ("province".equals(region.getLevel())) {
               regionVo = new RegionVo(region);
                regionVos.add(regionVo);
                provinces.put(region.getId(), regionVo);
            } else {
                regionVo = provinces.get(region.getParentId());
                if (regionVo != null) {
                    regionVo.getChildren().add(new RegionVo(region));
                }
            }
        }
        return regionVos;
    }
}
