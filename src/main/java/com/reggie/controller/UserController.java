package com.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.reggie.commons.R;
import com.reggie.commons.ValidateCodeUtils;
import com.reggie.entity.User;
import com.reggie.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author chenxi
 * @version 1.0
 * @date 2023/3/20 17:00
 * @description
 */

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 验证码验证
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session) {
        String phone = user.getPhone();
        if (StringUtils.isNotEmpty(phone)) {
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用阿里云提供的短信服务API完成发送短信
            //SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            //todo 验证码相关内容，由于无相关短信服务资源，因此此部分并不实际操作
//            session.setAttribute(phone,code);
//            使用redis数据库进行缓存验证码
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.success("手机验证码验证成功");
        }
        return R.error("短信发送失败");
    }

    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session) {
        log.info(map.toString());
        String phone = map.get("phone").toString();
        String code = map.get("code").toString();
//        String code1 = session.getAttribute(phone).toString();
        //从redis中获取验证码
        Object code1 = redisTemplate.opsForValue().get(phone);
        if (code1.equals(code)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone, phone);
            User user = new User();
            user.setPhone(phone);
            userService.saveOrUpdate(user, queryWrapper);
            session.setAttribute("user",user.getId());
            //验证成功，移除redis中的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }
        return R.error("登陆失败");
    }
}
