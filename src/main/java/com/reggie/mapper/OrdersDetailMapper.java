package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/21 16:56
 * @description
 */
@Mapper
public interface OrdersDetailMapper extends BaseMapper<OrderDetail> {
}
