package com.fc.ishop.controller.permission;

import com.fc.ishop.dos.Menu;
import com.fc.ishop.dto.MenuSearchParams;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.MenuService;
import com.fc.ishop.vo.MenuVo;
import com.fc.ishop.vo.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 管理员权限菜单管理
 */
@RestController
@RequestMapping("/menu")
public class MenuManagerController {
    @Autowired
    private MenuService menuService;

    /**
     * 搜索菜单
     *
     * @param searchParams
     * @return
     */
    @GetMapping
    public ResultMessage<List<Menu>> searchPermissionList(MenuSearchParams searchParams) {
        return ResultUtil.data(menuService.searchList(searchParams));
    }

    /**
     * 添加菜单
     */
    @PostMapping
    public ResultMessage<Menu> add(Menu menu) {
        menuService.save(menu);
        return ResultUtil.data(menu);
    }

    /**
     * 编辑菜单
     *
     * @param id
     * @param menu
     * @return
     */
    @PutMapping("/{id}")
    public ResultMessage<Menu> edit(@PathVariable String id, Menu menu) {
        if (id == null || menu == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        menu.setId(id);
        try {
            menuService.updateById(menu);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServiceException(ResultCode.ERROR);
        }
        return ResultUtil.data(menu);
    }

    /**
     * 删除菜单
     *
     * @param ids 菜单id
     * @return
     */
    @DeleteMapping("/{ids}")
    public ResultMessage<Menu> delByIds(@PathVariable List<String> ids) {
        if (ids == null || ids.size() == 0) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        menuService.deleteIds(ids);
        return ResultUtil.success();
    }

    /**
     * 获取菜单权限
     */
    @GetMapping("/tree")
    public ResultMessage<List<MenuVo>> getAllMenuList() {
        return ResultUtil.data(menuService.tree());
    }

    @GetMapping("/memberMenu")
    public ResultMessage<List<MenuVo>> memberMenu() {
        AuthUser currentUser = UserContext.getCurrentUser();
        return ResultUtil.data(menuService.findUserTree(currentUser));
    }
}
