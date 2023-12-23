package com.fc.ishop.utils;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SearchVo;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author florence
 * @date 2023/12/2
 */
public class PageUtil {

    public static <T> QueryWrapper<T> initWrapper(Object object) {
        return initWrapper(object, null);
    }
    /**
     * 查询条件存在有日期区间
     * @return
     */
    public static <T> QueryWrapper<T> initWrapper(Object object, SearchVo searchVo) {
        QueryWrapper<T> wrapper = new QueryWrapper<>();
        // 创建时间区间判定
        if (searchVo != null
                && StrUtil.isNotBlank(searchVo.getStartDate())
                && StrUtil.isNotBlank(searchVo.getEndDate())) {
            DateTime start = DateUtil.parse(searchVo.getStartDate());
            DateTime end = DateUtil.parse(searchVo.getEndDate());
            wrapper.between("create_time", start, DateUtil.endOfDay(end));
        }
        if (object != null) {
            String[] fieldName = BeanUtil.getFieldName(object);
            for (String key : fieldName) { // 遍历属性

                Object value = BeanUtil.getFieldValueByName(key, object);
                if (value != null && !"".equals(value)) {
                    wrapper.eq(StringUtils.camel2Underline(key), value);
                }
            }
        }
        return wrapper;
    }

    /**
     * 处理前端发送的页面参数请求，将其规范化
     * @param pagevo
     * @param <T>
     * @return
     */
    public static <T> Page<T> initPage(PageVo pagevo) {
        Page<T> page;
        // 获取基本信息
        int pageNumber = pagevo.getPageNumber();
        int pageSize = pagevo.getPageSize();
        String sort = pagevo.getSort();
        String order = pagevo.getOrder();

        if (pageNumber < 1) {
            pageNumber = 1;
        }
        if (pageSize < 10) {
            pageSize = 10;
        }
        page = new Page<>(pageNumber, pageSize);
        if (!StringUtils.isBlank(sort)) {
            boolean isAsc = false;
            if (!StringUtils.isBlank(order)) {
                if ("asc".equals(order.toLowerCase(Locale.ROOT))) {
                    isAsc = true;
                }
            }
            if (isAsc) {
                page.addOrder(OrderItem.asc(sort));
            } else {
                page.addOrder(OrderItem.desc(sort));
            }
        }

        return page;
    }

    public static <T> List<T> listToPage(PageVo page, List<T> list) {
        int pageNumber = page.getPageNumber() - 1;
        int pageSize = page.getPageSize();

        if (pageNumber < 0) {
            pageNumber = 0;
        }
        if (pageSize < 1) {
            pageSize = 10;
        }
        if (pageSize > 100) {
            pageSize = 100;
        }

        int fromIndex = pageNumber * pageSize;
        int toIndex = pageNumber * pageSize + pageSize;

        if (fromIndex > list.size()) {
            return new ArrayList<>();
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }
}
