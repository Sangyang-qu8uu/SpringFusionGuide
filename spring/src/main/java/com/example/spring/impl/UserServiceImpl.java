package com.example.spring.impl;

import com.example.spring.UserDao;
import com.example.spring.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
@Service
public class UserServiceImpl implements UserService {
    // 属性名可以叫 dao，也可以叫 userDao，但为了规范，建议和 setter 对应叫 userDao
    @Resource
    private UserDao userDao;

    // 必须保留无参构造，给 <property> 注入使用
    public UserServiceImpl() {
    }

    // 如果要用 Setter 注入，就不要写这个有参构造了，或者把它删掉！
    // 如果留着，可能会导致 Spring 不知道该用哪个。

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void sleep() {
        System.out.println("【目标对象】正在睡觉...");
        // 这里如果 userDao 注入成功，就没问题
        userDao.run();
    }

    @Override
    public void eat(String food) {
        System.out.println("【目标对象】正在吃：" + food);
    }
}
