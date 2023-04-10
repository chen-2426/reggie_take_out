package com.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.reggie.entity.Setmeal;
import com.reggie.mapper.SetmealMapper;
import com.reggie.service.SetmealService;
import org.springframework.stereotype.Service;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 16:51
 * @description
 */
@Service
public class SetmealSericeImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
}
