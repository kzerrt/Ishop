package com.fc.ishop.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.category.Category;
import com.fc.ishop.dos.goods.Goods;
import com.fc.ishop.dos.goods.GoodsGallery;
import com.fc.ishop.dto.GoodsOperationDto;
import com.fc.ishop.enums.GoodsAuthEnum;
import com.fc.ishop.enums.GoodsStatusEnum;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.GoodsMapper;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.*;
import com.fc.ishop.utils.BeanUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.dto.GoodsSearchParams;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.StoreVo;
import com.fc.ishop.vo.goods.GoodsSkuVo;
import com.fc.ishop.vo.goods.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author florence
 * @date 2023/12/11
 */
@Service("goodsService")
@Transactional
public class GoodsServiceImpl
        extends ServiceImpl<GoodsMapper, Goods> implements GoodsService {
    // 商品相册
    @Autowired
    private GoodsGalleryService goodsGalleryService;
    // 商品规格
    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private CategoryService categoryService;
    // 商品属性
    @Autowired
    private GoodsParamsService goodsParamsService;

    @Override
    public Integer todayUpperNum() {
        LambdaQueryWrapper<Goods> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(Goods::getMarketEnable, GoodsStatusEnum.UPPER.name());
        queryWrapper.gt(Goods::getCreateTime, DateUtil.beginOfDay(new DateTime()));
        return this.count(queryWrapper);
    }

    @Override
    public int goodsNum(String goodsStatusEnum, String goodsAuthEnum) {
        LambdaQueryWrapper<Goods> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Goods::getDeleteFlag, false);
        if (!StringUtils.isEmpty(goodsStatusEnum)) {
            queryWrapper.eq(Goods::getMarketEnable, goodsStatusEnum);
        }
        if (!StringUtils.isEmpty(goodsAuthEnum)) {
            queryWrapper.eq(Goods::getIsAuth, goodsAuthEnum);
        }
        return this.count(queryWrapper);
    }

    @Override
    public int getGoodsCountByCategory(String categoryId) {
        QueryWrapper<Goods> queryWrapper = Wrappers.query();
        queryWrapper.like("category_path", categoryId);
        return this.count(queryWrapper);
    }

    @Override
    public Page<Goods> queryByParams(GoodsSearchParams goodsSearchParams) {
        return this.page(PageUtil.initPage(goodsSearchParams), goodsSearchParams.queryWrapper());
    }

    @Override
    public boolean updateGoodsMarketAble(List<String> goodsIds, GoodsStatusEnum goodsStatusEnum, String underReason) {
        LambdaUpdateWrapper<Goods> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(Goods::getMarketEnable, goodsStatusEnum.name());
        updateWrapper.set(Goods::getUnderMessage, underReason);
        updateWrapper.in(Goods::getId, goodsIds);
        this.update(updateWrapper);

        //修改规格商品
        goodsSkuService.updateGoodsSkuStatusByIds(goodsIds, goodsStatusEnum);
        /*List<Goods> goodsList = this.list(new LambdaQueryWrapper<Goods>().in(Goods::getId, goodsIds));
        for (Goods goods : goodsList) { // 不同因素有很多，不能同意处理
            goodsSkuService.updateGoodsSkuStatus(goods);
        }*/
        return true;

    }

    @Override
    public boolean auditGoods(List<String> goodsIds, GoodsAuthEnum goodsAuthEnum) {
        boolean result = false;
        // 批量修改商品审核状态
        LambdaUpdateWrapper<Goods> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(Goods::getId, goodsIds);
        updateWrapper.set(Goods::getIsAuth, goodsAuthEnum.name());
        result = this.update(updateWrapper);
        goodsSkuService.updateAuthStatusByIds(goodsIds, goodsAuthEnum);
        //
        for (String goodsId : goodsIds) {
            Goods goods = this.checkExist(goodsId);
            goods.setIsAuth(goodsAuthEnum.name());
            // todo 商品审核消息发送
            /*//商品审核消息
            String destination = rocketmqCustomProperties.getGoodsTopic() + ":" + GoodsTagsEnum.GOODS_AUDIT.name();
            //发送mq消息
            rocketMQTemplate.asyncSend(destination,
                    JSONUtil.toJsonStr(goods.getStoreId()), RocketmqSendCallbackBuilder.commonCallback());*/
        }
        return result;
    }

    @Override
    public GoodsVo getGoodsVo(String id) {
        // 查询商品信息
        Goods goods = checkExist(id);
        GoodsVo goodsVo = new GoodsVo();
        BeanUtil.copyProperties(goods, goodsVo);

        goodsVo.setId(goods.getId());
        // 商品相册赋值
        List<String> images = new ArrayList<>();
        List<GoodsGallery> galleryList = goodsGalleryService.getGoodsListByGoodsId(id);
        for (GoodsGallery goodsGallery : galleryList) { // 获取图片路径
            images.add(goodsGallery.getOriginal());
        }
        goodsVo.setGoodsGalleryList(images);

        // 商品sku赋值
        List<GoodsSkuVo> goodsSkuList = goodsSkuService.getGoodsListByGoodsId(id);
        if (goodsSkuList != null && !goodsSkuList.isEmpty()) {
            goodsVo.setSkuList(goodsSkuList);
        }

        // 商品分类名称赋值
        List<String> categoryName = new ArrayList<>();
        String[] strArray = goods.getCategoryPath().split(",");
        List<Category> categories = categoryService.listByIds(Arrays.asList(strArray));
        for (Category category : categories) {
            categoryName.add(category.getName());
        }
        goodsVo.setCategoryName(categoryName);

        goodsVo.setGoodsParamsList(goodsParamsService.getGoodsParamsByGoodsId(id));
        return goodsVo;
    }

    @Override
    public void underStoreGoods(String storeId) {
        baseMapper.underStoreGoods(storeId);
    }

    @Override
    public void addGoods(GoodsOperationDto goodsOperationDTO) {
        Goods goods = new Goods(goodsOperationDTO);
        //判定商品是否需要审核
        goods.setIsAuth(GoodsAuthEnum.TOBEAUDITED.name());

        // 向goods加入图片
        this.setGoodsGalleryParam(goodsOperationDTO.getGoodsGalleryList().get(0), goods);

        // 购买次数
        goods.setBuyCount(0);

        this.save(goods);

        // 添加商品参数
        if (goodsOperationDTO.getGoodsParamsList() != null && !goodsOperationDTO.getGoodsParamsList().isEmpty()) {
            this.goodsParamsService.addParams(goodsOperationDTO.getGoodsParamsList(), goods.getId());
        }

        // 添加商品sku信息
        this.goodsSkuService.add(goodsOperationDTO.getSkuList(), goods);

        // 添加相册
        if (goodsOperationDTO.getGoodsGalleryList() != null && !goodsOperationDTO.getGoodsGalleryList().isEmpty()) {
            this.goodsGalleryService.add(goodsOperationDTO.getGoodsGalleryList(), goods.getId());
        }
    }
    /**
     * 添加商品默认图片
     *
     * @param origin 图片
     * @param goods  商品
     */
    private void setGoodsGalleryParam(String origin, Goods goods) {
        goods.setOriginal(origin);
        goods.setSmall(origin);
        goods.setThumbnail(origin);
    }
    /**
     * 判断商品是否存在
     *
     * @param goodsId
     * @return
     */
    private Goods checkExist(String goodsId) {
        Goods goods = getById(goodsId);
        if (goods == null) {
            log.error("商品ID为" + goodsId + "的商品不存在");
            throw new ServiceException(ResultCode.GOODS_NOT_EXIST);
        }
        return goods;
    }
}
