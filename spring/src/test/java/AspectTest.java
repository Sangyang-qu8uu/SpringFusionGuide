import com.example.aop.CglibProxyFactory;
import com.example.aop.JdkProxyFactory;
import com.example.config.SpringConfiguration;
import com.example.spring.OrderService;
import com.example.spring.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */

// 1. 使用 @RunWith 注解替换原来的运行期（让 JUnit 跑在 Spring 环境中）
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 核心配置类（代替了原来的 new AnnotationConfigApplicationContext(...)）
// @ContextConfiguration(value = {"classpath:applicationContext.xml"})
@ContextConfiguration(classes = {SpringConfiguration.class})
public class AspectTest {

    @Autowired
    private UserService userService;


    @Test
    public void jdkProxyFactoryTest() {

        // 1. 生成代理对象
        UserService proxy = JdkProxyFactory.getProxy(userService);
        // 2. 调用代理对象的方法
        proxy.sleep();
        System.out.println("-------------------");
        proxy.eat("苹果");
    }

    @Test
    public void cglibProxyFactoryTest() {

        // 1. 创建目标对象
        OrderService target = new OrderService();
        // 2. 生成代理对象
        OrderService proxy = CglibProxyFactory.getProxy(target);
        // 3. 调用代理对象的方法
        proxy.createOrder();
    }
}
