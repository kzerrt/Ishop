package com.fc.ishop.controller.file;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.constant.SendParam;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import com.fc.ishop.web.manager.FileManagerClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员 文件管理接口
 * @author florence
 * @date 2023/11/29
 */
@RestController
@RequestMapping("/file")
public class FileManagerController {
    @Autowired
    private FileManagerClient fileManagerClient;

    /**
     * 管理所有图片
     * @return
     */
    @GetMapping
    public ResultMessage<Page<IFile>> adminFiles(IFile file, SearchVo searchVo, PageVo pageVo) {
        Map<String, Object> send = new HashMap<>();
        send.put(SendParam.iFile, file);
        send.put(SendParam.pageVo, pageVo);
        send.put(SendParam.searchVo, searchVo);
        return fileManagerClient.adminFiles(send);
    }

    /**
     * 文件重命名
     * @param id 文件id
     * @param name 重命名
     */
    @PostMapping("/rename")
    public ResultMessage<IFile> upload(String id, String name) {
        return fileManagerClient.upload(id, name);
    }
    @DeleteMapping("/delete/{ids}")
    public ResultMessage<Object> delete(@PathVariable List<String> ids) {
        return fileManagerClient.delete(ids);
    }
}
