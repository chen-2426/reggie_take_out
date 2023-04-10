package com.reggie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.reggie.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/8 13:23
 * @description
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
