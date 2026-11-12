package com.example.config;

import org.springframework.context.annotation.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
//spring标志核心配置类
@Configuration
//包扫描
@ComponentScan("com.example")
//导入其他配置
@Import(DataSourceConfiguration.class)
@EnableAspectJAutoProxy // 👈 加上这个，AOP 才会生效
@EnableTransactionManagement // ⭐️ 开启基于注解的声明式事务
public class SpringConfiguration {


}
