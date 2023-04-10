package com.reggie.controller;

import com.reggie.commons.R;
import com.reggie.entity.AddressBook;
import com.reggie.entity.Orders;
import com.reggie.service.OrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/21 17:02
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrdersService ordersService;

    public R<String> submit(@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("结算成功");
    }

}
