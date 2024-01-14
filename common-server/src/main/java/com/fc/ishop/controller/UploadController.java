package com.fc.ishop.controller;

import com.fc.ishop.dos.IFile;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.prop.UploadProperties;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.FileService;
import com.fc.ishop.utils.CommonUtil;
import com.fc.ishop.utils.FileUtil;
import com.fc.ishop.vo.ResultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * 文件加载类
 * @author florence
 * @date 2023/11/18
 */
@Slf4j
@RestController
@RequestMapping("/upload")
public class UploadController {
    @Autowired
    private FileService fileService;
    @Autowired
    private void initFileUtil( UploadProperties uploadProperties) {
        FileUtil.setUpload(uploadProperties);
    }

    // todo 用户上传文件时在不保存的情况下依然会将文件下载
    @PostMapping("/file")
    public ResultMessage<String> upload(@RequestBody MultipartFile file) {
        if (file == null) {
            throw new ServiceException(ResultCode.OSS_EXCEPTION);
        }
        // 必定获取到用户
        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser == null) {
            throw new ServiceException(ResultCode.OSS_EXCEPTION);
        }
        // 文件重命名按时间戳的形式命名
        String rename = CommonUtil.renameByDate(file.getOriginalFilename());
        // 将文件信息保存到数据库
        IFile newFile = new IFile();
        newFile.setCreateBy(authUser.getUsername());
        newFile.setName(rename);
        newFile.setFileSize(file.getSize());
        newFile.setFileType(file.getContentType());
        // 文件相对路径
        //newFile.setUrl(relativePath);
        newFile.setUserEnums(authUser.getRole().name());
        if (authUser.getRole().equals(UserEnums.STORE)) {
            newFile.setOwnerId(authUser.getStoreId());
        } else {
            newFile.setOwnerId(authUser.getId());
        }
        try {
            newFile.setUrl(FileUtil.uploadFile(file, rename,
                    newFile.getUserEnums(), newFile.getOwnerId()/* 文件存放位置路径*/));
            fileService.save(newFile);
        } catch (IOException e) {
            e.printStackTrace();
            try { // 文件删除
                FileUtil.deleteFile(newFile.getUrl());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            throw new ServiceException(ResultCode.OSS_EXCEPTION);
        }
        // 将文件名称发送到前端
        log.debug("上传的图片路径为：{}", newFile.getUrl());
        return ResultUtil.data(newFile.getUrl());
    }
}
