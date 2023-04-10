package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/20 17:01
 * @description
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
