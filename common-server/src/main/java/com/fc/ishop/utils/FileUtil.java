package com.fc.ishop.utils;

import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.prop.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 * @author florence
 * @date 2023/12/2
 */
@Slf4j
public class FileUtil {
    private static UploadProperties upload;
    /**
     * 文件上传,返回不带文件名称的相对路径
     */
    public static String uploadFile(MultipartFile file, String fileName) throws IOException {
        return uploadFile(file, fileName, (String) null);

    }

    /**
     * @param paths 需要添加的文件路径，除基本路径外，可以额外添加专属路径
     * @return
     * @throws IOException
     */
    public static String uploadFile(MultipartFile file, String fileName, String... paths) throws IOException {
        if (file == null) {
            throw new ServiceException(ResultCode.OSS_EXCEPTION);
        }
        String contentType = file.getContentType();
        String relativePath = "/other/";
        if (contentType != null && contentType.contains("image")) {
            // 文件路径格式 /imag/oos/xx.png
            relativePath = upload.getImgRelativePath() + "/" + StringUtils.getRelativePath(paths) + fileName;
        } else {
            relativePath += StringUtils.getRelativePath(paths) + fileName;
        }
        File uploadFile = new File(upload.getBasePath() + relativePath);
        // 路径如果存在，就创建
        FileUtils.forceMkdirParent(uploadFile);
        // 剪切，将文件复制到路径中
        file.transferTo(uploadFile);
        // 返回相对路径名
        return relativePath;
    }

    /**
     * 删除文件
     * @param relativeFile 文件相对路径
     * @throws IOException
     */
    public static void deleteFile(String relativeFile) throws IOException {
        if (StringUtils.isEmpty(relativeFile)) return;
        // 对路径进行检查，并返回正确路径
        relativeFile = StringUtils.checkPath(relativeFile);
        // todo 检查文件路径是否正确
        String absolutePath = upload.getBasePath() + relativeFile;
        File file = new File(absolutePath);
        // 文件是否存在
        if (!file.exists()) return;
        // 如果是目录，就不删除
        if (file.isDirectory()) return;

        // 强制删除文件
        FileUtils.forceDelete(file);
    }
    public static void setUpload(UploadProperties upload) {
        log.info("upload配置类加载......");
        FileUtil.upload = upload;
    }
}
