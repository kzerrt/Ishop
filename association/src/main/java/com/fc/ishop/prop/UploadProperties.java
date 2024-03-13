package com.fc.ishop.prop;

import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.utils.StringUtils;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 文件加载配置类
 * @author florence
 * @date 2023/11/18
 */
@Data
@Component
@ConfigurationProperties("upload")
public class UploadProperties  {
    private String commonHost;
    private String staticAccessPath;
    private String basePath;
    private String upload;
    private String image;
    private String video;

    public String getImgRelativePath() {
        if (!StringUtils.isEmpty(upload) && !StringUtils.isEmpty(image)) {
            return upload + image;
        }
        return "/upload/image";
    }
    public String getImgRelativePath(UserEnums userEnums) {
        if (!StringUtils.isEmpty(upload) && !StringUtils.isEmpty(image)) {
            return upload + image + "/" +userEnums.name() + "/";
        }
        return "/upload/image";
    }
    public String getImgAbsolutePath() {
        if (StringUtils.isEmpty(basePath)) {
            throw new NullPointerException("文件路径错误");
        }
        return basePath + getImgRelativePath();
    }

}
