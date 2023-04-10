package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.entity.Orders;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/21 16:57
 * @description
 */
public interface OrdersService extends IService<Orders> {
    void submit(Orders orders);
}
