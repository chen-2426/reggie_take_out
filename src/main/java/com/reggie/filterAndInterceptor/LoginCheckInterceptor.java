package com.reggie.filterAndInterceptor;

import com.reggie.commons.BaseContext;
import com.reggie.commons.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/9 21:25
 * @description
 */
@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object employee = request.getSession().getAttribute("employee");
        Object user = request.getSession().getAttribute("user");
        if (employee != null || user != null) {
            BaseContext.setCurrentId(Long.parseLong(employee.toString()));
            return true;
        }
        response.getWriter().write(
                new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(
                        R.error("NOTLOGIN")
                )
        );
        return false;
    }
}
