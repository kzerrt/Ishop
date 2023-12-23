package com.fc.ishop.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fc.ishop.constant.Count;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.dto.FileOwnerDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.enums.UserEnums;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.FileService;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author florence
 * @date 2023/11/28
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileService fileService;

    /**
     * 获取自己的图片资源
     * 名称模糊匹配
     */
    @GetMapping
    public ResultMessage<IPage<IFile>> getFileList(@RequestHeader(Count.AccessToken) String accessToken, IFile file, SearchVo searchVo, PageVo pageVo) {
        AuthUser authUser = UserContext.getCurrentInAll(accessToken);
        FileOwnerDto fileOwnerDto = new FileOwnerDto();
        // 只有买家才写入id
        if (authUser.getRole().equals(UserEnums.MEMBER)) {
            fileOwnerDto.setOwnerId(authUser.getId());
        } else if (authUser.getRole().equals(UserEnums.STORE)){
            fileOwnerDto.setOwnerId(authUser.getStoreId());
        }
        fileOwnerDto.setUserEnums(authUser.getRole().name());
        return ResultUtil.data(fileService.customerPageOwner(fileOwnerDto, file, searchVo, pageVo));
    }
    @PostMapping("/rename")
    public ResultMessage<IFile> upload(@RequestHeader(Count.AccessToken) String accessToken, String id, String newName) {
        AuthUser authUser = UserContext.getCurrentInAll(accessToken);
        IFile file = fileService.getById(id);
        file.setName(newName);
        // 对图片进行身份认证
        if (!file.getUserEnums().equals(authUser.getRole().name())) {
            throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
        }
        switch (authUser.getRole()) {
            case MEMBER:
                if (file.getOwnerId().equals(authUser.getId())) {
                    break;
                }
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            case STORE:
                if (file.getOwnerId().equals(authUser.getStoreId())) {
                    break;
                }
                throw new ServiceException(ResultCode.USER_AUTHORITY_ERROR);
            case MANAGER:
                break;
        }
        fileService.updateById(file);
        return ResultUtil.data(file);
    }
    /**
     *
     * @param accessToken 文件服务中不存在filter，无法通过security获取
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    public ResultMessage<Object> delete(@RequestHeader(Count.AccessToken) String accessToken, @PathVariable List<String> ids) {
        AuthUser authUser = UserContext.getCurrentInAll(accessToken);
        fileService.batchDelete(ids, authUser);
        return ResultUtil.success();
    }
}
