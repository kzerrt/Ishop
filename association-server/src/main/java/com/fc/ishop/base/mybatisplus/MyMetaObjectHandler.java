package com.fc.ishop.base.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.fc.ishop.security.AuthUser;
import com.fc.ishop.security.context.UserContext;
import com.fc.ishop.utils.SnowFlake;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * mybatis-p对字段进行填充
 * tips 在进行插入时使用雪花算法设置id
 * @author florence
 * @date 2023/11/28
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        AuthUser auth = UserContext.getCurrentUser();
        if (auth != null) {
            this.setFieldValByName("createBy", auth.getUsername(), metaObject);
        }
        //添加插入时间
        this.setFieldValByName("createTime", new Date(), metaObject);
        // 有值，则插入
        if (metaObject.hasGetter("deleteFlag")) {
            this.setFieldValByName("deleteFlag", false, metaObject);
        }
        // 写入插入id
        if (metaObject.hasGetter("id")) {
            this.setFieldValByName("id", SnowFlake.getIdStr(), metaObject);
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        AuthUser authUser = UserContext.getCurrentUser();
        if (authUser != null) {
            this.setFieldValByName("updateBy", authUser.getUsername(), metaObject);
        }
        this.setFieldValByName("updateTime", new Date(), metaObject);
    }
}
