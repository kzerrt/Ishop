package com.fc.ishop.dto;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author florence
 * @date 2023/12/15
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GoodsSearchParams extends PageVo {
    private static final long serialVersionUID = 2544015852728566887L;

    /**
     * 商品编号
     */
    private String goodsId;

    /**
     *商品名称
     */
    private String goodsName;

    /**
     *商品编号
     */
    private String sn;

    /**
     *商家ID
     */
    private String storeId;

    /**
     *卖家名字
     */
    private String storeName;

    /**
     *价格,可以为范围，如10_1000
     */
    private String price;

    /**
     *分类path
     */
    private String categoryPath;

    /**
     *是否是积分商品
     */
    private Boolean isPoint;

    /**
     *店铺分类id
     */
    private String storeCategoryPath;

    /**
     *是否自营
     */
    private Boolean selfOperated;

    /**
     * 上下架状态
     * @see com.fc.ishop.enums.GoodsStatusEnum
     */
    private String marketEnable;

    /**
     * 审核状态
     * @see com.fc.ishop.enums.GoodsAuthEnum
     */
    private String isAuth;

    /**
     *库存数量
     */
    private Integer quantity;

    /**
     *是否为推荐商品
     */
    private Boolean recommend;

    public <T> QueryWrapper<T> queryWrapper() {
        QueryWrapper<T> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(goodsId)) {
            queryWrapper.eq("goods_id", goodsId);
        }
        if (StringUtils.isNotEmpty(goodsName)) {
            queryWrapper.like("goods_name", goodsName);
        }
        if (StringUtils.isNotEmpty(sn)) {
            queryWrapper.eq("sn", sn);
        }
        if (StringUtils.isNotEmpty(storeId)) {
            queryWrapper.eq("store_id", storeId);
        }
        if (StringUtils.isNotEmpty(storeName)) {
            queryWrapper.like("store_name", storeName);
        }
        if (StringUtils.isNotEmpty(categoryPath)) {
            queryWrapper.like("category_path", categoryPath);
        }
        if (isPoint != null) {
            queryWrapper.eq("is_point", isPoint);
        }
        if (StringUtils.isNotEmpty(storeCategoryPath)) {
            queryWrapper.like("store_category_path", storeCategoryPath);
        }
        if (selfOperated != null) {
            queryWrapper.eq("self_operated", selfOperated);
        }
        if (StringUtils.isNotEmpty(marketEnable)) {
            queryWrapper.eq("market_enable", marketEnable);
        }
        if (StringUtils.isNotEmpty(isAuth)) {
            queryWrapper.eq("is_auth", isAuth);
        }
        if (quantity != null) {
            queryWrapper.le("quantity", quantity);
        }
        if (recommend != null) {
            queryWrapper.le("recommend", recommend);
        }
        queryWrapper.eq("delete_flag", false);
        this.betweenWrapper(queryWrapper);
        return queryWrapper;
    }

    private <T> void betweenWrapper(QueryWrapper<T> queryWrapper) {
        if (StringUtils.isNotEmpty(price)) {
            String[] s = price.split("_");
            if (s.length > 1) {
                queryWrapper.ge("price", s[1]);
            } else {
                queryWrapper.le("price", s[0]);
            }
        }
    }
}
