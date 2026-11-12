package com.example.aop;

import org.springframework.stereotype.Component;

@Component // 交给 Spring 管理
public class Target implements TargetInterface {
    @Override
    public void save() {
        System.out.println("【Target】save running....");
        // 假设这里如果发生异常，后面可以演示异常通知
//         int i = 1/0;
    }
}
