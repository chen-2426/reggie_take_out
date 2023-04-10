package com.reggie.commons;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/8 14:17
 * @description 一个用于通用的结果封装类
 */
@Data
public class R<T> {
    private Integer code;
    private String msg;
    private T data;
    private Map map = new HashMap();

    public static <T> R<T> success(T object) {
        R<T> r = new R<>();
        r.data = object;
        r.code = 1;
        return r;
    }

    public static <T> R<T> error(String msg) {
        R<T> r = new R<>();
        r.msg = msg;
        r.code = 0;
        return r;
    }

    public R<T> add(String key, Object value) {
        this.map.put(key, value);
        return this;
    }
}
