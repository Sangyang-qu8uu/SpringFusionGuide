package com.example.aop;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
import com.example.spring.UserService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    // 传入目标对象，返回增强后的代理对象
    public static UserService getProxy(final UserService target) {
        /*
         * ClassLoader: 目标对象的类加载器
         * Class[]: 目标对象实现的接口（JDK 代理必须是接口）
         * InvocationHandler: 增强逻辑（在这里写前置/后置通知）
         */
        return (UserService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 1. 前置增强
                        System.out.println("【动态代理】--> 方法执行前：记录日志/开启事务");

                        // 2. 执行目标方法
                        Object result = method.invoke(target, args);

                        // 3. 后置增强
                        System.out.println("【动态代理】--> 方法执行后：提交事务");

                        return result;
                    }
                }
        );
    }
}
