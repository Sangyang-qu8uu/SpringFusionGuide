
import com.example.config.SpringConfiguration;
import com.example.tx.PlatformTransferService;
import com.example.tx.TransactionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class) // 加载你的核心配置类
public class TransactionTest {

    // 1. 注入我们写好的转账业务类
    @Autowired
    private PlatformTransferService platformTransferService;

    @Autowired
    private TransactionService transferService;

    // 2. 注入 JdbcTemplate，用于在测试前后查数据库验证
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDeclarativeTransaction() {
        System.out.println("========== 开始测试声明式事务 ==========");

        // 测试前：查询张三和李四的初始金额
        Double zhangSanMoneyBefore = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "张三");
        Double liSiMoneyBefore = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "李四");

        System.out.println("转账前 -> 张三金额: " + zhangSanMoneyBefore + ", 李四金额: " + liSiMoneyBefore);

        // 3. 执行转账（⚠️ 注意：此时你的 Service 代码里必须有人为制造异常的代码，如 int i = 1/0;）
        try {
            // 假设张三给李四转 100 块钱
            platformTransferService.transfer("张三", "李四", 100.0);
            System.out.println("转账正常执行完毕。");
        } catch (Exception e) {
            // 因为我们故意制造了除零异常，这里一定会捕获到
            System.out.println("❌ 转账过程中发生异常，已被测试类捕获！异常信息: " + e.getMessage());
        }

        // 测试后：再次查询张三和李四的金额
        Double zhangSanMoneyAfter = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "张三");
        Double liSiMoneyAfter = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "李四");

        System.out.println("转账后 -> 张三金额: " + zhangSanMoneyAfter + ", 李四金额: " + liSiMoneyAfter);

        // 4. 断言验证（判断事务是否回滚）
        System.out.println("---------- 验证结果 ----------");
        if (zhangSanMoneyBefore.equals(zhangSanMoneyAfter) && liSiMoneyBefore.equals(liSiMoneyAfter)) {
            System.out.println("✅ 验证成功：发生异常后，金额没有变化，声明式事务【回滚】生效了！");
        } else {
            System.out.println("❌ 验证失败：发生异常后，金额居然变了，事务没有生效！请检查配置和注解！");
        }
    }

    @Test
    public void testProgrammaticTransaction() {
        System.out.println("========== 开始测试编程式事务 ==========");

        // 1. 转账前查余额
        Double zhangSanBefore = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "张三");
        Double liSiBefore = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "李四");
        System.out.println("转账前 -> 张三: " + zhangSanBefore + ", 李四: " + liSiBefore);

        // 2. 执行转账
        try {
            // 注意：你的 TransferService.transfer 里必须有 int i = 1 / 0; 模拟异常！
            transferService.transfer("张三", "李四", 100.0);
        } catch (Exception e) {
            // 捕获到你在编程式事务里抛出的 RuntimeException
            System.out.println("❌ 捕获到转账异常: " + e.getMessage());
        }

        // 3. 转账后查余额
        Double zhangSanAfter = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "张三");
        Double liSiAfter = jdbcTemplate.queryForObject(
                "SELECT money FROM account WHERE name = ?", Double.class, "李四");
        System.out.println("转账后 -> 张三: " + zhangSanAfter + ", 李四: " + liSiAfter);

        // 4. 断言验证
        System.out.println("---------- 验证结果 ----------");
        if (zhangSanBefore.equals(zhangSanAfter) && liSiBefore.equals(liSiAfter)) {
            System.out.println("✅ 验证成功：发生异常后，金额没有变化，编程式事务【回滚】生效了！");
        } else {
            System.out.println("❌ 验证失败：金额变了，事务没有生效！");
        }
    }
}
