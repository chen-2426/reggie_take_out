package com.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.DTO.DishDTO;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.mapper.DishMapper;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 16:50
 * @description
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Override
    @Transactional
    public void addDishAndFlavor(DishDTO dishDto) {
        //保存菜品
        this.save(dishDto);
        Long dishId = dishDto.getId();
        //保存味道
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((item) -> {     //todo 这种写法没见过，研究一下以及Foreach方法
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public DishDTO getDishAndFlavor(Long id) {
        Dish dish = this.getById(id);
        DishDTO dishDTO = new DishDTO();
        BeanUtils.copyProperties(dish, dishDTO);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> list = dishFlavorService.list(queryWrapper);
        dishDTO.setFlavors(list);
        return dishDTO;
    }

    @Override
    public void updateDishAndFlavor(DishDTO dishDto) {
        this.updateById(dishDto);
        LambdaQueryWrapper<DishFlavor> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId, dishDto.getId());
        dishFlavorService.remove(queryWrapper);
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }
}
