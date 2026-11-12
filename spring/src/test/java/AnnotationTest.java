import com.example.annotation.AnnotationDemo;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */
public class AnnotationTest {


    /**
     * 通过注解方式获取配置值
     */
    @Test
    public void test1() {
        // 使用注解配置上下文，并指定扫描的包
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.annotation");

        // 获取 Bean
        AnnotationDemo demo = context.getBean("annotationDemo", AnnotationDemo.class);


        // 打印测试
        System.out.println("Driver: " + demo.getDriver());
        System.out.println("Str: " + demo.getStr());

        context.close();
    }

    /**
     *测试@Value注解
     */
    @Test
    public void test2() {
        // 使用注解配置上下文，并指定扫描的包
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.annotation");

        // 获取 Bean
        AnnotationDemo demo = context.getBean("annotationDemo", AnnotationDemo.class);

        AnnotationDemo demo2 = context.getBean("annotationDemo", AnnotationDemo.class);
        System.out.println(demo2.equals(demo));

        context.close();
    }
}
