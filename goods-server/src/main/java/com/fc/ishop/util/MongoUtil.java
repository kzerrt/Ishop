package com.fc.ishop.util;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ArrayUtil;
import com.fc.ishop.dto.SecKillSearchParams;
import com.fc.ishop.enums.PromotionApplyStatusEnum;
import com.fc.ishop.enums.PromotionStatusEnum;
import com.fc.ishop.vo.PageVo;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * mongoDB工具类
 * @author florence
 * @date 2023/12/21
 */
public class MongoUtil {

    /**
     * 创建query对象
     * @param queryParam
     * @return
     */
    public static Query creatMongoQuery(SecKillSearchParams queryParam) {
        String goodsName = queryParam.getGoodsName();
        String promotionName = queryParam.getPromotionName();
        String seckillId = queryParam.getSeckillId();
        String[] storeIds = queryParam.getStoreIds();
        Integer timeLine = queryParam.getTimeLine();
        Long startTime = queryParam.getStartTime();
        Long endTime = queryParam.getEndTime();
        String promotionApplyStatus = queryParam.getPromotionApplyStatus();
        String promotionStatus = queryParam.getPromotionStatus();

        Query query = new Query();
        if (CharSequenceUtil.isNotEmpty(goodsName)) {
            Pattern pattern = Pattern.compile("^.*" + goodsName + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("goodsName").regex(pattern));
        }
        if (CharSequenceUtil.isNotEmpty(promotionName)) {
            Pattern pattern = Pattern.compile("^.*" + promotionName + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("promotionName").regex(pattern));
        }
        if (CharSequenceUtil.isNotEmpty(seckillId)) {
            query.addCriteria(Criteria.where("_id").is(seckillId));
        }
        if (storeIds != null) {
            Pattern pattern = Pattern.compile("^.*" + ArrayUtil.join(storeIds, ",") + ".*$", Pattern.CASE_INSENSITIVE);
            query.addCriteria(Criteria.where("storeIds").regex(pattern));
        }
        if (timeLine != null) {
            query.addCriteria(Criteria.where("timeLine").is(timeLine));
        }
        if (startTime != null) {
            query.addCriteria(Criteria.where("startTime").gte(new Date(startTime)));
        }
        if (endTime != null) {
            query.addCriteria(Criteria.where("endTime").lte(new Date(endTime)));
        }
        if (CharSequenceUtil.isNotEmpty(promotionApplyStatus)) {
            query.addCriteria(Criteria.where("promotionApplyStatus").is(PromotionApplyStatusEnum.valueOf(promotionApplyStatus).name()));
        }
        if (CharSequenceUtil.isNotEmpty(promotionStatus)) {
            query.addCriteria(Criteria.where("promotionStatus").is(PromotionStatusEnum.valueOf(promotionStatus).name()));
        }
        query.addCriteria(Criteria.where("deleteFlag").is(false));
        return query;
    }
    /**
     * 为mongoQuery组织分页排序参数
     *
     * @param query 查询条件
     * @param page  分页排序参数
     */
    public static void mongoQueryPageParam(Query query, PageVo page) {
        page.setNotConvert(true);
        query.with(PageRequest.of(page.getMongoPageNumber(), page.getPageSize()));
        if (!CharSequenceUtil.isEmpty(page.getOrder()) && !CharSequenceUtil.isEmpty(page.getSort())) {
            query.with(Sort.by(Sort.Direction.valueOf(page.getOrder().toUpperCase()), page.getSort()));
        }
    }
}
