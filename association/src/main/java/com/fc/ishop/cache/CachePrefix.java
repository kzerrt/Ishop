package com.fc.ishop.cache;

import com.fc.ishop.enums.PromotionTypeEnum;
import com.fc.ishop.enums.UserEnums;

/**
 * 缓存前缀信息
 */
public enum CachePrefix {
    /**
     * NONCE
     */
    NONCE,
    /**
     * 在线人数
     */
    ONLINE_NUM,
    /**
     * token信息
     */
    ACCESS_TOKEN,
    /**
     * 权限
     */
    PERMISSION_LIST,
    /**
     * 部门id列表
     */
    DEPARTMENT_IDS,
    /**
     * 商品分类
     */
    CATEGORY,
    /**
     * 系统设置
     */
    SETTING,
    /**
     * 商品详情
     */
    GOODS_SKU,
    /**
     * 商品库存
     */
    SKU_STOCK,
    /**
     * 商品
     */
    GOODS;


    public static String removePrefix(String str) {
        return str.substring(str.lastIndexOf("}_" + 2));
    }
    public String getPrefix() {
        return "{" + this.name() +"}_";
    }
    public String getPrefix(PromotionTypeEnum typeEnum) {
        return "{" +this.name() + "_" + typeEnum.name() + "}_";
    }
    //获取缓存key值 + 用户端，例如：三端都有用户体系，需要分别登录，如果用户名一致，则redis中的权限可能会冲突出错
    public String getPrefix(UserEnums user) {
        return "{" + this.name() + "_" + user.name() + "}_";
    }
}
