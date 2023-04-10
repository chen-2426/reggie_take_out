package com.reggie.commons;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/11 12:43
 * @description 自动注入数据
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
/*
    客户端每次发送一个http请求,都会创建一个新的线程
    每个请求无论调用了多少服务器中的方法，都属于此次调用的这个线程中
    因此可以利用ThreadLocal实现获取更改对象的方法
    PS：
    ThreadLocal并非一个Thread，而是Thread的一个局部变量；
    ThreadLocal用于维护变量时会产生一个独立的变量副本
    所以每个线程都可以拥有一个独立的副本，从而实现线程隔离
    ThreadLocal得到的内容只能从线程内部访问，外部无法访问
    相关方法写入BaseContext工具类中
    */
    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("createUser",BaseContext.getCurrentId() );
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
    }
}
