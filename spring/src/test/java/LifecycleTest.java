
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

public class LifecycleTest {


    /**
     * 通过注解方式获取配置值
     */
    @Test
    public void testLifecycle() {
        // 1. 启动容器
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext("com.example.annotation");

        // 2. 关闭容器（放在 try 块里，或者直接放在最后）
        context.close();
    }


}
