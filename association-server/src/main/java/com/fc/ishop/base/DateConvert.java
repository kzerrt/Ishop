package com.fc.ishop.base;

import com.fc.ishop.utils.DateUtil;
import com.fc.ishop.utils.StringUtils;
import org.springframework.core.convert.converter.Converter;

import java.util.Date;


/**
 * @author florence
 * @date 2023/12/25
 */
public class DateConvert implements Converter<String, Date> {
    @Override
    public Date convert(String s) {
        if (StringUtils.isEmpty(s)) {
            return null;
        }
        return DateUtil.toDate(new Date(s), DateUtil.STANDARD_DATE_FORMAT);
    }
}
