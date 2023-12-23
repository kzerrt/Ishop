package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.StoreDetail;
import com.fc.ishop.vo.StoreDetailVo;
import org.apache.ibatis.annotations.Select;

/**
 * @author florence
 * @date 2023/12/18
 */
public interface StoreDetailMapper extends BaseMapper<StoreDetail> {
    @Select("select s.store_logo,s.member_name,s.store_name,s.store_disable,s.self_operated,s.store_address_detail,s.store_address_path,s.store_address_id_path,s.store_center,s.store_desc,d.* " +
            "from li_store s inner join li_store_detail d on s.id=d.store_id where s.id=#{storeId}")
    StoreDetailVo getDetail(String storeId);
}
