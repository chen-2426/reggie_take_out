package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.reggie.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/20 22:46
 * @description
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
}
