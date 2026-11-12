package com.example.annotation;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class LifecycleBean {

    public LifecycleBean() {
        System.out.println("1. 构造方法执行：Bean 被创建了");
    }

    @PostConstruct
    public void init() {
        // 当 Bean 创建好，并且属性注入（比如 @Autowired、@Value）完成后，执行此方法
        System.out.println("2. @PostConstruct 执行：Bean 的初始化方法被调用了");
    }

    @PreDestroy
    public void destroy() {
        // 当 Spring 容器关闭或销毁 Bean 之前，执行此方法
        System.out.println("3. @PreDestroy 执行：Bean 的销毁方法被调用了");
    }
}
