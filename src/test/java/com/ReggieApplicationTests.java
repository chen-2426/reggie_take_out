package com;

import com.reggie.entity.Employee;
import com.reggie.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;

@SpringBootTest
class ReggieApplicationTests {



    @Test
    void contextLoads() {
    }

    public int count;

    @Test
    public int number(int i) {
        if (i == 1) return 1;
        return 0;
    }

}
