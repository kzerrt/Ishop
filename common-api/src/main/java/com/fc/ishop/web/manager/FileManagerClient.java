package com.fc.ishop.web.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fc.ishop.dos.IFile;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 管理端 文件管理
 * @author florence
 * @date 2023/12/10
 */
@FeignClient("common-server")
public interface FileManagerClient {
    /**
     * 获取所有图片
     * IFile , SearchVo , PageVo
     * @return
     */
    @PostMapping("/manager/file")
    ResultMessage<Page<IFile>> adminFiles(@RequestBody Map<String, Object> params);

    /**
     * 文件重命名
     * @param id
     * @param name
     * @return
     */
    @PutMapping("/manager/file/rename")
    ResultMessage<IFile> upload(@RequestParam("id") String id,@RequestParam("name") String name);

    /**
     * 批量删除文件
     * @param ids
     * @return
     */
    @DeleteMapping("/delete/{ids}")
    ResultMessage<Object> delete(@PathVariable List<String> ids);
}
