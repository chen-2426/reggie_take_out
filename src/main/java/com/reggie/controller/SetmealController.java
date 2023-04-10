package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.reggie.DTO.SetmealDTO;
import com.reggie.commons.R;
import com.reggie.entity.Dish;
import com.reggie.entity.Setmeal;
import com.reggie.entity.SetmealDish;
import com.reggie.service.CategoryService;
import com.reggie.service.SetmealDishService;
import com.reggie.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 19:18
 * @description
 */
@RestController
@Slf4j
@RequestMapping("/setmeal")
public class SetmealController {
    @Autowired
    private SetmealService setmealService;
    @Autowired
    private SetmealDishService setmealDishService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/page")
    public R<Page> page(Integer page, Integer pageSize, String name) {
        Page<Setmeal> pageInfo = new Page<>(page, pageSize);
        Page<SetmealDTO> DTOpageInfo = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name),
                Setmeal::getName, name
        ).orderByAsc(Setmeal::getCode);
        setmealService.page(pageInfo, queryWrapper);
        List<Setmeal> setmeals = pageInfo.getRecords();
        List<SetmealDTO> setmealDTOList = setmeals.stream().map((item) -> {
            SetmealDTO setmealDTO = new SetmealDTO();
            BeanUtils.copyProperties(item, setmealDTO);
            Long categoryId = item.getCategoryId();
            setmealDTO.setCategoryName(categoryService.getById(categoryId).getName());
            return setmealDTO;
        }).collect(Collectors.toList());
        DTOpageInfo.setRecords(setmealDTOList);
        return R.success(DTOpageInfo);
    }

    @PostMapping
    public R<String> addSetmeal(SetmealDTO setmealDto) {
        setmealService.save(setmealDto);
        List<SetmealDish> setmealDishList = setmealDto.getSetmealDishes();
        setmealDishList.stream().map((item) -> {
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishList);
        return R.success("添加成功");
    }

    @DeleteMapping
    @Transactional //事物注解
    public R<String> delete(@RequestParam List<Long> ids) {
        return null;
    }

    @PostMapping("/status/{status}")
    public R<List<Setmeal>> update(@RequestParam List<Long> ids, @PathVariable Integer status) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        List<Setmeal> list = setmealService.list(queryWrapper);
        list.stream().map((item) -> {
            item.setStatus(status);
            return item;
        }).collect(Collectors.toList());
        setmealService.updateBatchById(list);
        return R.success(list);
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(@RequestBody Setmeal setmeal) {
        LambdaQueryWrapper<Setmeal> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(setmeal.getCategoryId() != null, Setmeal::getId, setmeal.getCategoryId())
                .eq(setmeal.getStatus() != null, Setmeal::getStatus, setmeal.getStatus())
                .orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> list = setmealService.list(queryWrapper);
        return R.success(list);
    }
}
