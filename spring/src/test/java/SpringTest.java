import com.alibaba.druid.pool.DruidDataSource;
import com.example.config.SpringConfiguration;
import com.example.spring.UserDao;
import com.example.spring.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;

public class SpringTest {
    @Test
    public void test1() {
        // 1. 加载核心配置文件（Spring 5 推荐使用 ClassPathXmlApplicationContext）
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 2. 通过 id 获取 Bean 实例
        UserDao userDao = (UserDao) context.getBean("userDao");

        // 3. 调用方法测试
        userDao.run();

    }


    @Test
    public void test2() {
        // 1. 加载核心配置文件（Spring 5 推荐使用 ClassPathXmlApplicationContext）
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

        // 2. 通过 id 获取 Bean 实例
        UserService  userService = (UserService) context.getBean("userService");

        // 3. 调用方法测试
        userService.sleep();
    }

    @Test
    public void test3() {
        // 1. 加载核心配置文件（Spring 5 推荐使用 ClassPathXmlApplicationContext）
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml",
                "applicationContext-service.xml");

        // 2. 通过 id 获取 Bean 实例
        UserService  userService = (UserService) context.getBean("userService");

        // 3. 调用方法测试
        userService.sleep();
    }


    @Test
    public void test4() {
        // 1. 加载核心配置类，启动 Spring 容器
        System.out.println("========== 开始启动 Spring 容器 ==========");
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);

        // ================== 验证 1：@ComponentScan ==================
        System.out.println("\n---------- 验证 @ComponentScan (包扫描) ----------");
        try {
            UserService userService = context.getBean(UserService.class);
            System.out.println("✅ 成功获取到 UserService: " + userService.getClass().getName());
        } catch (Exception e) {
            System.out.println("❌ 失败：没有扫描到 UserService");
        }

        // ================== 验证 2：@Import + @Bean ==================
        System.out.println("\n---------- 验证 @Import 和 @Bean ----------");
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            System.out.println("✅ 成功获取到 DataSource: " + dataSource.getClass().getName());
        } catch (Exception e) {
            System.out.println("❌ 失败：没有获取到 DataSource");
        }

        // ================== 验证 3：@PropertySource ==================
        System.out.println("\n---------- 验证 @PropertySource (配置文件读取) ----------");
        try {
            DataSource dataSource = context.getBean(DataSource.class);
            // 把 DataSource 强转成 DruidDataSource，看看里面的属性有没有被注入
            DruidDataSource druid = (DruidDataSource) dataSource;
            System.out.println("✅ 读取到的 URL: " + druid.getUrl());
            System.out.println("✅ 读取到的 Username: " + druid.getUsername());
            System.out.println("✅ 读取到的 Password: " + druid.getPassword());
        } catch (Exception e) {
            System.out.println("❌ 失败：配置文件的属性没有注入到 DataSource 中");
            e.printStackTrace();
        }

        // ================== 验证 4：@Configuration 本身 ==================
        System.out.println("\n---------- 验证 @Configuration 本身 ----------");
        Object configBean = context.getBean("springConfiguration");
        System.out.println("✅ 配置类本身也被注册为 Bean: " + configBean.getClass().getName());

        // 关闭容器
        context.close();
        System.out.println("\n========== 测试结束 ==========");
    }
}
