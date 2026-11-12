import com.example.config.SpringConfiguration;
import org.junit.runner.RunWith;
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

import com.example.spring.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

// 1. 使用 @RunWith 注解替换原来的运行期（让 JUnit 跑在 Spring 环境中）
@RunWith(SpringJUnit4ClassRunner.class)
// 加载 Spring 核心配置类（代替了原来的 new AnnotationConfigApplicationContext(...)）
// @ContextConfiguration(value = {"classpath:applicationContext.xml"})
@ContextConfiguration(classes = {SpringConfiguration.class})
public class SpringJunitTest {

    // 2. 使用 @Autowired 注入需要测试的对象
    // 注意：此时对象由 Spring 自动注入，千万不要再自己 new 了
    @Autowired
    private UserService userService;

    @Autowired
    private DataSource dataSource;

    // 3. 创建测试方法进行测试
    @Test
    public void testUserService() {
        System.out.println("---------- 测试 UserService ----------");
        // 直接调用方法，不需要再从 context 中 getBean
        userService.sleep();
        System.out.println("✅ UserService 测试成功，对象为: " + userService.getClass().getName());
    }

    @Test
    public void testDataSource() {
        System.out.println("---------- 测试 DataSource ----------");
        System.out.println("✅ 成功注入 DataSource: " + dataSource.getClass().getName());
        // 如果之前配置了 properties，这里依然可以看到 url 等属性
    }
}
