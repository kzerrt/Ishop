package com.fc.ishop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.dto.FileOwnerDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.mapper.FileMapper;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.service.FileService;
import com.fc.ishop.utils.FileUtil;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author florence
 * @date 2023/11/18
 */
@Slf4j
@Service("fileService")
public class FileServiceImpl
        extends ServiceImpl<FileMapper, IFile> implements FileService {
    @Override
    public IPage<IFile> customerPageOwner(FileOwnerDto fileOwnerDto, IFile file, SearchVo searchVo, PageVo pageVo) {
        LambdaQueryWrapper<IFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StringUtils.isNotEmpty(fileOwnerDto.getOwnerId()), IFile::getOwnerId, fileOwnerDto.getOwnerId())
                .eq(IFile::getUserEnums, fileOwnerDto.getUserEnums())
                .like(StringUtils.isNotEmpty(file.getName()), IFile::getName, file.getName())
                /*.like(StringUtils.isNotEmpty(file.getFileKey()), IFile::getFileKey, file.getFileKey())*/
                .like(StringUtils.isNotEmpty(file.getFileType()), IFile::getFileType, file.getFileType())
                .between(StringUtils.isNotEmpty(searchVo.getStartDate()) && StringUtils.isNotEmpty(searchVo.getEndDate()),
                        IFile::getCreateTime, searchVo.getStartDate(), searchVo.getEndDate());
        return this.page(PageUtil.initPage(pageVo), queryWrapper);
    }

    @Override
    public void batchDelete(List<String> ids, AuthUser authUser) {
        LambdaQueryWrapper<IFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(IFile::getId, ids);
        queryWrapper.eq(IFile::getUserEnums, authUser.getRole().name());

        // 操作图片属性判定
        switch (authUser.getRole()) {
            case MEMBER:
                queryWrapper.eq(IFile::getOwnerId, authUser.getId());
                break;
            case STORE:
                queryWrapper.eq(IFile::getOwnerId, authUser.getStoreId());
                break;
            case MANAGER:
                break;
            default:
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        // 根据条件查询文件信息
        List<IFile> files = this.list(queryWrapper);
        String relateFile = null;// 记录删除文件的名称
        // 在本地删除文件
        try {
            for (IFile file : files) {
                relateFile = file.getUrl();
                FileUtil.deleteFile(relateFile);
            }
        } catch (IOException e) {
            log.error("用户id: {} 文件删除失败,删除的文件为：{}\n{}", authUser.getId(), relateFile, e);
            throw new ServiceException(ResultCode.OSS_DEL_FILE_ERROR);
        }
        //删除数据库中数据
        this.remove(queryWrapper);
    }

    @Override
    public Page<IFile> customerPage(IFile file, SearchVo searchVo, PageVo pageVo) {
        LambdaQueryWrapper<IFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotEmpty(file.getName()), IFile::getName, file.getName())
                .like(StringUtils.isNotEmpty(file.getFileType()), IFile::getFileType, file.getFileType())
                .between(StringUtils.isNotEmpty(searchVo.getStartDate()) && StringUtils.isNotEmpty(searchVo.getEndDate()),
                IFile::getCreateTime, searchVo.getStartDate(), searchVo.getEndDate());

        return this.page(PageUtil.initPage(pageVo), query());
    }

    @Override
    public void batchDelete(List<String> ids) {
        LambdaQueryWrapper<IFile> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(IFile::getId, ids);
        // 获取文件本地地址
        List<IFile> files = this.list(queryWrapper);
        String relateFile = null;// 记录删除文件的名称
        // 在本地删除文件
        try {
            for (IFile file : files) {
                relateFile = file.getUrl();
                FileUtil.deleteFile(relateFile);
            }
        } catch (IOException e) {
            log.error("管理员 文件删除失败,删除的文件为：{}\n{}", relateFile, e);
            throw new ServiceException(ResultCode.OSS_DEL_FILE_ERROR);
        }
        //删除数据库中数据
        this.remove(queryWrapper);
    }
}
