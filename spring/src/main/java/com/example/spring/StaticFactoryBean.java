package com.example.spring;

import com.example.spring.impl.UserDaoImpl;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description StaticFactoryBean
 * @since JDK 11
 */
public class StaticFactoryBean {

    public static UserDao createUserDao(){
        return new UserDaoImpl();
    }
}
