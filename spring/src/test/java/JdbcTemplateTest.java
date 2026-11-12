
import com.example.jdbc.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = com.example.config.SpringConfiguration.class) // 加载注解配置
public class JdbcTemplateTest {

    // 注入 JdbcTemplate
    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 1. 测试新增
    @Test
    public void testInsert() {
        String sql = "INSERT INTO account (name, money) VALUES (?, ?)";
        int update = jdbcTemplate.update(sql, "李四", 2000.0);
        System.out.println("✅ 新增成功，受影响行数: " + update);
    }

    // 2. 测试删除
    @Test
    public void testDelete() {
        String sql = "DELETE FROM account WHERE id = ?";
        int update = jdbcTemplate.update(sql, 2);
        System.out.println("✅ 删除成功，受影响行数: " + update);
    }

    // 3. 测试修改
    @Test
    public void testUpdate() {
        String sql = "UPDATE account SET money = ? WHERE name = ?";
        int update = jdbcTemplate.update(sql, 5000.0, "张三");
        System.out.println("✅ 修改成功，受影响行数: " + update);
    }

    // 4. 测试查询：查询单个对象
    @Test
    public void testQueryOne() {
        String sql = "SELECT * FROM account WHERE id = ?";
        // BeanPropertyRowMapper 会自动把查询结果映射到实体类中（要求字段名和属性名一致）
        Account account = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Account.class), 1);
        System.out.println("✅ 查询单个结果: " + account);
    }

    // 5. 测试查询：查询列表
    @Test
    public void testQueryList() {
        String sql = "SELECT * FROM account";
        List<Account> accountList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Account.class));
        for (Account account : accountList) {
            System.out.println(account);
        }
    }

    // 6. 测试查询：查询总数（返回单一数值）
    @Test
    public void testQueryCount() {
        String sql = "SELECT COUNT(*) FROM account";
        Long count = jdbcTemplate.queryForObject(sql, Long.class);
        System.out.println("✅ 账户总数: " + count);
    }
}
