package com.reggie.controller;

import com.reggie.entity.Employee;
import com.reggie.service.EmployeeService;
import com.reggie.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/16 11:47
 * @description
 */
public class test {

//
//    @Test
//    void save() {
//        EmployeeService employeeService = new EmployeeServiceImpl();
//        Employee employee = new Employee();
//        employee.setId(2L);
//        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setCreateUser(1L);
//        employee.setUpdateUser(1L);
//        employee.setStatus(1);
//        employee.setName("test");
//        employee.setUsername("test");
//        employee.setPhone("13212345678");
//        employee.setIdNumber("111222333444555666");
//        employee.setSex("0");
//        System.out.println(employee);
//        employeeService.save(employee);
//    }
}
