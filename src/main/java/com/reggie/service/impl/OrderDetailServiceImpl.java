package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.OrderDetail;
import com.reggie.mapper.OrdersDetailMapper;
import com.reggie.service.OrderDetailService;
import org.springframework.stereotype.Service;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/21 16:59
 * @description
 */

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrderDetailService {
}
