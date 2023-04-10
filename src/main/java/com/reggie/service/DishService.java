package com.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.reggie.DTO.DishDTO;
import com.reggie.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 16:49
 * @description
 */
public interface DishService extends IService<Dish> {
    public void addDishAndFlavor(DishDTO dishDto);
}
