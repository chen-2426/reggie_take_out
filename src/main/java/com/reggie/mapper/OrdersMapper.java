package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/21 16:55
 * @description
 */
@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
