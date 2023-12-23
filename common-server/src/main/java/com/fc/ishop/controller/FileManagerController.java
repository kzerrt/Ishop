package com.fc.ishop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.service.FileService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import com.fc.ishop.web.manager.FileManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 管理端 文件管理
 * @author florence
 * @date 2023/12/10
 */
@RestController
public class FileManagerController implements FileManagerClient {
    @Autowired
    private FileService fileService;

    @Override
    public ResultMessage<Page<IFile>> adminFiles(Map<String, Object> params) {
        PageVo pageVo = (PageVo) params.get(SendParam.pageVo);
        IFile file = (IFile) params.get(SendParam.iFile);
        SearchVo searchVo = (SearchVo) params.get(SendParam.searchVo);
        return ResultUtil.data(fileService.customerPage(file, searchVo, pageVo));
    }

    @Override
    public ResultMessage<IFile> upload(String id, String name) {
        IFile file = fileService.getById(id);
        file.setName(name);
        fileService.updateById(file);
        return ResultUtil.success();
    }

    @Override
    public ResultMessage<Object> delete(List<String> ids) {
        fileService.batchDelete(ids);
        return ResultUtil.success();
    }
}
