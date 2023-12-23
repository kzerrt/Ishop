package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.goods.GoodsUnit;
import com.fc.ishop.mapper.GoodsUnitMapper;
import com.fc.ishop.service.GoodsUnitService;
import org.springframework.stereotype.Service;

/**
 * @author florence
 * @date 2023/12/13
 */
@Service("goodsUnitService")
public class GoodsUnitServiceImpl
        extends ServiceImpl<GoodsUnitMapper, GoodsUnit> implements GoodsUnitService {
}
