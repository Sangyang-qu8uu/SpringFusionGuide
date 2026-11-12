package com.example.annotation;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
@Repository("annotationDemo")
@PropertySource("classpath:jdbc.properties")
@Scope("singleton")
//@Scope("prototype")
public class AnnotationDemo {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("注入普通数据")
    private String str;

    public String getDriver() {
        return driver;
    }

    public String getStr() {
        return str;
    }
}
