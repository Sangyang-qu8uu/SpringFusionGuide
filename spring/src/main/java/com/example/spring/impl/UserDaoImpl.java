package com.example.spring.impl;

import com.example.spring.UserDao;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description userDao
 * @since JDK 11
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Override
    public void run() {
        System.out.println("spring 容器 hello world");
    }
}
