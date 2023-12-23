package com.fc.ishop.utils;

import org.springframework.beans.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 对象处理工具类
 */
public class BeanUtil {

    public static void copyProperties(Object objectFrom, Object objectTo) {
        BeanUtils.copyProperties(objectFrom, objectTo);
    }
    public static String[] getFieldName(Object o) {
        if (o == null) return null;
        Field[] fields = o.getClass().getDeclaredFields();
        Field[] superFields = o.getClass().getSuperclass().getDeclaredFields();
        String[] fieldNames = new String[fields.length + superFields.length];
        int index = 0;
        for (int i = 0; i < fields.length; i++) {
            fieldNames[index] = fields[i].getName();
            index++;
        }
        for (int i = 0; i < superFields.length; i++) {
            if (superFields[i].getName().equals("id")) {
                continue;
            }
            fieldNames[index] = superFields[i].getName();
            index++;
        }
        return fieldNames;
    }

    public static Object getFieldValueByName(String fieldName, Object object) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = object.getClass().getMethod(getter);
            return method.invoke(object);
        } catch (Exception e) {
            //e.printStackTrace();
            return null;
        }
    }
}
