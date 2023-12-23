package com.fc.ishop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.dto.FileOwnerDto;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SearchVo;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/10
 */
public interface FileService extends IService<IFile> {
    // 所属文件数据查询
    IPage<IFile> customerPageOwner(FileOwnerDto fileOwnerDto, IFile file, SearchVo searchVo, PageVo pageVo);
    // 通过id批量删除文件
    void batchDelete(List<String> ids, AuthUser authUser);
    // 查看所有图片
    IPage<IFile> customerPage(IFile file, SearchVo searchVo, PageVo pageVo);

    void batchDelete(List<String> ids);
}