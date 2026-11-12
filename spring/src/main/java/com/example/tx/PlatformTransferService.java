package com.example.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatformTransferService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 加上这个注解，这个方法就自动拥有事务了！
    @Transactional(rollbackFor = Exception.class)
    public void transfer(String fromName, String toName, Double money) {
        // 张三扣钱
        String sqlOut = "UPDATE account SET money = money - ? WHERE name = ?";
        jdbcTemplate.update(sqlOut, money, fromName);

        // 模拟异常（此时数据库会回滚，张三的钱不会少）
         int i = 1 / 0;

        // 李四加钱
        String sqlIn = "UPDATE account SET money = money + ? WHERE name = ?";
        jdbcTemplate.update(sqlIn, money, toName);
    }
}
