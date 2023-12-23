package com.fc.ishop.controller.passport;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fc.ishop.aop.LimitPoint;
import com.fc.ishop.constant.Count;
import com.fc.ishop.dos.AdminUser;
import com.fc.ishop.dto.AdminUserDto;
import com.fc.ishop.enums.ResultCode;
import com.fc.ishop.enums.ResultUtil;
import com.fc.ishop.exception.ServiceException;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.service.DepartmentService;
import com.fc.ishop.service.UserService;
import com.fc.ishop.token.Token;
import com.fc.ishop.utils.PageUtil;
import com.fc.ishop.utils.StringUtils;
import com.fc.ishop.vo.AdminUserVo;
import com.fc.ishop.vo.PageVo;
import com.fc.ishop.vo.ResultMessage;
import com.fc.ishop.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 管理员接口
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserManagerController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;

    /**
     * todo 解决相同用户从登录页面登录产生多个token问题
     */
    //@LimitPoint(name = "userLogin")
    @GetMapping("/login")
    public ResultMessage<Token> login(String username, String password) {
        return ResultUtil.data(userService.login(username, password));
    }
    @GetMapping("/refresh/{refreshToken}")
    public ResultMessage<Object> refreshToken(@PathVariable String refreshToken) {
        if (refreshToken == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        return ResultUtil.data(userService.refreshToken(refreshToken));
    }

    /**
     * 获取登录用户的信息(own)
     */
    @GetMapping("/info")
    public ResultMessage<AdminUser> getUserInfoByOwn() {
        //AuthUser authUser = UserContext.getCurrentUser(accessToken);
        AuthUser authUser = UserContext.getCurrentUser();
        AdminUserVo adminUserVo = new AdminUserVo(userService.findByUsername(authUser.getUsername()));
        // 搜寻部门id
        if (!StringUtils.isEmpty(adminUserVo.getDepartmentId())) {
            adminUserVo.setDepartmentTitle(departmentService.getById(adminUserVo.getDepartmentId()).getTitle());
        }
        adminUserVo.setPassword(null);
        /*adminUserVo.setCreateTime(null);
        adminUserVo.setUpdateTime(null);*/
        return ResultUtil.data(adminUserVo);
    }

    //@GetMapping("/info")
    /*public ResultMessage<AdminUser> getUserInfo() {
        AuthUser tokenUser = UserContext.getCurrentUser();
        if (tokenUser == null) {
            throw new ServiceException(ResultCode.USER_NOT_LOGIN);
        }
        AdminUserVo adminUserVo = new AdminUserVo(userService.findByUsername(tokenUser.getUsername()));
        // 搜寻部门id
        if (!StringUtils.isEmpty(adminUserVo.getDepartmentId())) {
            adminUserVo.setDepartmentTitle("zheshi ");
        }
        adminUserVo.setPassword(null);
        return ResultUtil.data(adminUserVo);
    }*/

    /**
     * 管理员修改自己账号信息
     * 用户名密码不修改
     */
    @PutMapping("/edit")
    public ResultMessage<Object> editOwner(@RequestHeader(Count.AccessToken) String accessToken, AdminUser adminUser) {
        if (adminUser == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        AuthUser tokenUser = UserContext.getCurrentUser(accessToken);
        // 从数据库中查找
        AdminUser adminUserDB = userService.findByUsername(tokenUser.getUsername());
        adminUserDB.setAvatar(adminUser.getAvatar());
        adminUserDB.setNickName(adminUser.getNickName());
        if (!userService.updateById(adminUserDB)) {
            throw new ServiceException(ResultCode.USER_EDIT_ERROR);
        }
        return ResultUtil.success(ResultCode.USER_EDIT_SUCCESS);
    }

    /**
     * 管理员修改其他成员资料
     */
    @PutMapping("/admin/edit")
    public ResultMessage<Object> edit(AdminUser adminUser,
                                      @RequestParam(required = false)List<String> roles) {
        if (adminUser == null) {
            throw new ServiceException(ResultCode.PARAMS_ERROR);
        }
        if (!userService.updateAdminUser(adminUser, roles)) {
            throw new ServiceException(ResultCode.USER_EDIT_ERROR);
        }
        return ResultUtil.success(ResultCode.USER_EDIT_SUCCESS);
    }
    /**
     * 修改密码(own)
     */
    @PutMapping("/editPassword")
    public ResultMessage<Object> editPassword(@RequestHeader(Count.AccessToken) String accessToken, String password, String newPassword) {
        AuthUser tokenUser = UserContext.getCurrentUser(accessToken);
        userService.editPassword(tokenUser, password, newPassword);
        return ResultUtil.success(ResultCode.USER_EDIT_SUCCESS);
    }

    /**
     * 重置密码
     */
    @PostMapping("/resetPassword/{ids}")
    public ResultMessage<Object> resetPassword(@PathVariable List<String> ids) {
        userService.resetPassword(ids);
        return ResultUtil.success(ResultCode.USER_EDIT_SUCCESS);
    }

    /**
     * 多条件查询用户列表
     */
    @GetMapping
    public ResultMessage<Object> getByCondition(AdminUserDto user,
                                                SearchVo searchVo,
                                                PageVo pagevo) {
        IPage<AdminUserVo> page =
                userService.adminUserPage(PageUtil.initPage(pagevo), PageUtil.initWrapper(user, searchVo));
        return ResultUtil.data(page);
    }
    /**
     * 添加用户
     */
    @PostMapping
    public ResultMessage<Object> register(AdminUserDto userDto,
                                          @RequestParam(required = false) List<String> roles) {
        if (roles != null && roles.size() >= 10) {
            throw new ServiceException(ResultCode.PERMISSION_BEYOND_TEN);
        }
        if (StringUtils.isEmpty(userDto.getUsername()) || StringUtils.isEmpty(userDto.getPassword())) {
            throw new ServiceException("用户名或密码不能为空");
        }
        try {
            userService.saveAdminUser(userDto, roles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.success();
    }
    /**
     * 禁/启用户
     */
    @PutMapping("/enable/{userId}")
    public ResultMessage<Object> disable(@PathVariable String userId, Boolean status){
        AdminUser user = userService.getById(userId);
        if (user == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        if (user.getStatus().compareTo(status) != 0) {
            user.setStatus(status);
            userService.updateById(user);
        }
        return ResultUtil.success();
    }
    /**
     * 通过ids批量删除(彻底删除)
     */
    @DeleteMapping("/{ids}")
    public ResultMessage<Object> delAllByIds(@PathVariable List<String> ids) {
        userService.deleteCompletely(ids);
        return ResultUtil.success();
    }
}
