package com.fc.ishop.utils;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;

import java.util.Date;
import java.util.UUID;

/**
 * 通用类
 * @author florence
 * @date 2023/11/18
 */
public class CommonUtil {
    /**
     * 以uuid重命名
     * @return
     */
    public static String rename(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("上传文件名称为空");
        }
        int index = fileName.lastIndexOf('.');
        if (index < 0) {
            throw new ServiceException(ResultCode.OSS_FILE_ERROR);
        }
        String extName = fileName.substring(index);
        return UUID.randomUUID().toString().replace("-", "") + extName;
    }
    public static String renameByDate(String fileName) {
        if (fileName == null) {
            throw new NullPointerException("上传文件名称为空");
        }
        int index = fileName.lastIndexOf('.');
        if (index < 0) {
            throw new ServiceException(ResultCode.OSS_FILE_ERROR);
        }
        String extName = fileName.substring(index);
        return new Date().getTime() + extName;
    }


}
