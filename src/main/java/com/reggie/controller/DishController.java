package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.DTO.DishDTO;
import com.reggie.commons.R;
import com.reggie.entity.Category;
import com.reggie.entity.Dish;
import com.reggie.entity.DishFlavor;
import com.reggie.service.CategoryService;
import com.reggie.service.DishFlavorService;
import com.reggie.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 19:08
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/dish")
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private DishFlavorService dishFlavorService;
    @Autowired
    private RedisTemplate redisTemplate;


    @GetMapping("/{id}")
    public R<DishDTO> getById(@PathVariable String id) {
        Long dishid = Long.valueOf(id);
        DishDTO dishDTO = dishService.getDishAndFlavor(dishid);
        return R.success(dishDTO);
    }

    @PutMapping
    public R<String> updateDish(@RequestBody DishDTO dishDto) {

        dishService.updateDishAndFlavor(dishDto);
//        全局清理
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
//        精准清理
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDTO> DTOpageInfo = new Page<>(page, pageSize);

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),
                Dish::getName, name
        ).orderByAsc(Dish::getSort);
        dishService.page(pageInfo, queryWrapper);
        //copy对象
        BeanUtils.copyProperties(pageInfo, DTOpageInfo, "records");
        List<Dish> records = pageInfo.getRecords();
        List<DishDTO> list = records.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            String categoryName = category.getName();
            dishDTO.setCategoryName(categoryName);
            return dishDTO;

        }).collect(Collectors.toList());
        DTOpageInfo.setRecords(list);
        return R.success(DTOpageInfo);
    }

    @PostMapping
    public R<String> addDish(@RequestBody DishDTO dishDto) {

        dishService.addDishAndFlavor(dishDto);
//        全局清理
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
//        精准清理
        String key = "dish_" + dishDto.getCategoryId() + "_1";
        redisTemplate.delete(key);
        return R.success("添加成功");
    }

    /**
     * 根据类别查询菜品
     *
     * @param dish
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDTO>> List(Dish dish) {

        List<DishDTO> dishDTOList = null;
        //动态生成key
        String key = "dish_" + dish.getCategoryId() + "_" + dish.getStatus();
        // 引入redis，使用redis暂时缓存数据，（用于解决高并发情况下，反复查询数据库引起的缓慢问题）
        dishDTOList = (List<DishDTO>) redisTemplate.opsForValue().get(key);
        if (dishDTOList != null) {
            return R.success(dishDTOList);
        }

        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(dish.getCategoryId() != null, Dish::getCategoryId, dish.getCategoryId())
                .orderByAsc(Dish::getSort)
                .orderByDesc(Dish::getUpdateTime);
        List<Dish> list = dishService.list(queryWrapper);
        dishDTOList = list.stream().map((item) -> {
            DishDTO dishDTO = new DishDTO();
            BeanUtils.copyProperties(item, dishDTO);
            Long categoryId = item.getCategoryId();
            Category category = categoryService.getById(categoryId);
            if (categoryId != null) {
                String name = category.getName();
                dishDTO.setCategoryName(name);
            }
            Long id = item.getId();
            LambdaQueryWrapper<DishFlavor> dishFlavorLambdaQueryWrapper = new LambdaQueryWrapper<>();
            dishFlavorLambdaQueryWrapper.eq(DishFlavor::getDishId, id);
            List<DishFlavor> dishFlavorList = dishFlavorService.list(dishFlavorLambdaQueryWrapper);
            dishDTO.setFlavors(dishFlavorList);
            return dishDTO;
        }).collect(Collectors.toList());

        redisTemplate.opsForValue().set(key, dishDTOList, 60, TimeUnit.MINUTES);

        return R.success(dishDTOList);
    }

    @PostMapping("/status/{status}")
    public R<List<Dish>> update(@RequestParam List<Long> ids, @PathVariable Integer status) {
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId, ids);
        List<Dish> list = dishService.list(queryWrapper);
        list.stream().map((item) -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        dishService.updateBatchById(list);
        return R.success(list);
    }

}
