package com.example.aop;

import com.example.spring.OrderService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyFactory {

    public static OrderService getProxy(final OrderService target) {
        // 1. 创建 Enhancer 对象（CGLIB 的核心类）
        Enhancer enhancer = new Enhancer();
        // 2. 设置父类（因为 CGLIB 是继承，所以代理类是 target 的子类）
        enhancer.setSuperclass(target.getClass());

        // 3. 设置回调函数（类似于 InvocationHandler）
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                System.out.println("【CGLIB代理】--> 方法执行前：记录日志");
                // 执行目标方法
                Object result = method.invoke(target, objects);
                System.out.println("【CGLIB代理】--> 方法执行后：提交事务");
                return result;
            }
        });

        // 4. 创建并返回代理对象
        return (OrderService) enhancer.create();
    }
}
