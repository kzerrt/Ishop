package com.fc.ishop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fc.ishop.dos.goods.GoodsParams;
import com.fc.ishop.vo.goods.GoodsParamsVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author florence
 * @date 2023/12/12
 */
public interface GoodsParamsMapper extends BaseMapper<GoodsParams> {
    @Select("select p.*,gp.param_value,p.group_id  from i_parameters p left join i_goods_params gp on p.id=gp.param_id and gp.goods_id = #{goodsId}  where p.category_id = #{categoryId} order by sort")
    List<GoodsParamsVo> paramList(String goodsId, String categoryId);
}
