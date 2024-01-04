package com.fc.ishop.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.cache.Cache;
import com.fc.ishop.dos.SpecValues;
import com.fc.ishop.dos.Specification;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsSku;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.GoodsSkuMapper;
import com.fc.ishop.service.GoodsSkuService;
import com.fc.ishop.service.SpecValuesService;
import com.fc.ishop.service.SpecificationService;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.vo.SpecValueVo;
import com.fc.ishop.vo.SpecificationVo;
import com.fc.ishop.vo.goods.GoodsSkuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author florence
 * @date 2023/12/15
 */
@Service("goodsSkuService")
public class GoodsSkuServiceImpl
        extends ServiceImpl<GoodsSkuMapper, GoodsSku> implements GoodsSkuService {
    @Autowired
    private Cache<GoodsSku> cache;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private SpecificationService specificationService;
    @Autowired
    private SpecValuesService specValuesService;
    @Override
    public Page<GoodsSku> getGoodsSkuByPage(GoodsSearchParams searchParams) {
        return this.page(PageUtil.initPage(searchParams), searchParams.queryWrapper());
    }

    @Override
    public void updateGoodsSkuStatus(Goods goods) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GoodsSku::getGoodsId, goods.getId());
        updateWrapper.set(GoodsSku::getMarketEnable, goods.getMarketEnable());
        updateWrapper.set(GoodsSku::getIsAuth, goods.getIsAuth());
        this.update(updateWrapper);
    }

    @Override
    public void updateGoodsSkuStatusByIds(List<String> ids, GoodsStatusEnum goodsStatusEnum) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(GoodsSku::getGoodsId, ids);
        updateWrapper.set(GoodsSku::getMarketEnable, goodsStatusEnum.name());
        this.update(updateWrapper);
    }

    @Override
    public void updateAuthStatusByIds(List<String> ids, GoodsAuthEnum goodsAuthEnum) {
        LambdaUpdateWrapper<GoodsSku> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(GoodsSku::getGoodsId, ids);
        updateWrapper.set(GoodsSku::getIsAuth, goodsAuthEnum.name());
        this.update(updateWrapper);
    }

    @Override
    public List<GoodsSkuVo> getGoodsListByGoodsId(String goodsId) {
        List<GoodsSku> list = this.list(new LambdaQueryWrapper<GoodsSku>().eq(GoodsSku::getGoodsId, goodsId));
        return this.getGoodsSkuVoList(list);
    }
    @Override
    public List<GoodsSkuVo> getGoodsSkuVoList(List<GoodsSku> list) {
        List<GoodsSkuVo> goodsSkuVOS = new ArrayList<>();
        for (GoodsSku goodsSku : list) {
            GoodsSkuVo goodsSkuVO = this.getGoodsSkuVo(goodsSku);
            goodsSkuVOS.add(goodsSkuVO);
        }
        return goodsSkuVOS;
    }

    @Override
    public GoodsSku getGoodsSkuByIdFromCache(String id) {
        GoodsSku goodsSku = cache.get(GoodsSkuService.getCacheKeys(id));
        if (goodsSku == null) {
            goodsSku = this.getById(id);
            if (goodsSku == null) {
                return null;
            }
            cache.put(GoodsSkuService.getCacheKeys(id), goodsSku);
        }
        String quantity = stringRedisTemplate.opsForValue().get(GoodsSkuService.getStockCacheKey(id));
        if (quantity != null) {
            if (goodsSku.getQuantity() != Integer.parseInt(quantity)) {
                goodsSku.setQuantity(Integer.parseInt(quantity));
                this.updateById(goodsSku);
            }
        } else {
            stringRedisTemplate.opsForValue().set(GoodsSkuService.getStockCacheKey(id), goodsSku.getQuantity().toString());
        }

        return goodsSku;
    }

    @Override
    public void add(List<Map<String, Object>> skuList, Goods goods) {
        List<GoodsSku> newSkuList;
        // 如果有规格
        if (skuList != null && !skuList.isEmpty()) {
            // 添加商品sku
            newSkuList = this.addGoodsSku(skuList, goods);
        } else {
            throw new ServiceException("规格必须要有一个！");
        }
    }

    private GoodsSkuVo getGoodsSkuVo(GoodsSku goodsSku) {
        GoodsSkuVo goodsSkuVO = new GoodsSkuVo(goodsSku);
        JSONObject jsonObject = JSONUtil.parseObj(goodsSku.getSpecs());
        List<SpecValueVo> specValueVOS = new ArrayList<>();
        List<String> goodsGalleryList = new ArrayList<>();
        for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
            SpecValueVo s = new SpecValueVo();
            if (entry.getKey().equals("images")) {
                s.setSpecName(entry.getKey());
                if (entry.getValue().toString().contains("url")) {
                    List<SpecValueVo.SpecImages> specImages = JSONUtil.toList(JSONUtil.parseArray(entry.getValue()), SpecValueVo.SpecImages.class);
                    s.setSpecImage(specImages);
                    goodsGalleryList = specImages.stream().map(SpecValueVo.SpecImages::getUrl).collect(Collectors.toList());
                }
            } else {
                SpecificationVo specificationVO = new SpecificationVo();
                specificationVO.setSpecName(entry.getKey());
                specificationVO.setStoreId(goodsSku.getStoreId());
                specificationVO.setCategoryPath(goodsSku.getCategoryPath());
                Specification specification = specificationService.addSpecification(specificationVO);
                s.setSpecNameId(specification.getId());
                SpecValues specValues = specValuesService.getSpecValues(entry.getValue().toString(), specification.getId());
                s.setSpecValueId(specValues.getId());
                s.setSpecName(entry.getKey());
                s.setSpecValue(entry.getValue().toString());
            }
            specValueVOS.add(s);
        }
        goodsSkuVO.setGoodsGalleryList(goodsGalleryList);
        goodsSkuVO.setSpecList(specValueVOS);
        return goodsSkuVO;
    }
    /**
     * 增加sku集合
     *
     * @param skuList sku列表
     * @param goods   商品信息
     */
    private List<GoodsSku> addGoodsSku(List<Map<String, Object>> skuList, Goods goods) {
        List<GoodsSku> skus = new ArrayList<>();
        for (Map<String, Object> skuVO : skuList) {
            Map<String, Object> resultMap = this.add(skuVO, goods);
            GoodsSku goodsSku = (GoodsSku) resultMap.get("goodsSku");
            if (goods.getSelfOperated() != null) {
                goodsSku.setSelfOperated(goods.getSelfOperated());
            }
            skus.add(goodsSku);
            //stringRedisTemplate.opsForValue().set(GoodsSkuService.getStockCacheKey(goodsSku.getId()), goodsSku.getQuantity().toString());
        }
        this.saveBatch(skus);
        return skus;
    }
    /**
     * 添加商品规格
     *
     * @param map   规格属性
     * @param goods 商品
     * @return 规格商品
     */
    private Map<String, Object> add(Map<String, Object> map, Goods goods) {
        Map<String, Object> resultMap = new HashMap<>();
        GoodsSku sku = new GoodsSku();
        resultMap.put("goodsSku", sku);
        return resultMap;
    }
}
