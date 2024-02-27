package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.Menu;
import com.fc.ishop.vo.UserMenuVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> findByUserId(String userId);
//    @Select("SELECT rm.is_super,m.* FROM li_menu AS m INNER JOIN li_role_menu AS rm ON rm.menu_id=m.id WHERE rm.role_id IN (" +
//            "SELECT ur.role_id FROM li_user_role AS ur WHERE ur.user_id=#{userId}) OR rm.role_id IN (" +
//            "SELECT dr.role_id FROM li_department_role AS dr INNER JOIN li_admin_user AS au ON au.department_id=dr.department_id " +
//            "WHERE au.id=#{userId}) GROUP BY m.id,rm.is_super ORDER BY rm.is_super desc")
    List<UserMenuVo> getUserRoleMenu(String userId);
}
