package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 16:04
 * @description
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
