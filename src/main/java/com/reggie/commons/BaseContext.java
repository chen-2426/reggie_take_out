package com.reggie.commons;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/15 22:28
 * @description 基于ThreadLocal封装工具类，用于保存或获取当前ID
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id) {
        threadLocal.set(id);
    }

    public static long getCurrentId(){
        return threadLocal.get();
    }
}
