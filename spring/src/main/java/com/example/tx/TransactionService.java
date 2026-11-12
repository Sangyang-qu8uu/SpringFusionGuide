package com.example.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TransactionService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public void transfer(String fromName, String toName, Double money) {
        // 1. 事务外的操作（比如：查风控、发通知，绝不占用数据库连接）
        System.out.println("--- 执行事务外的前置逻辑 ---");

        // 2. 开启编程式事务（核心代码）
        // 只要 execute 内部的 lambda 报错，就会自动回滚；正常结束就自动提交。
        transactionTemplate.execute(status -> {
            try {
                // 张三扣钱
                jdbcTemplate.update("UPDATE account SET money = money - ? WHERE name = ?", money, fromName);

                // 模拟异常
                int i = 1 / 0;

                // 李四加钱
                jdbcTemplate.update("UPDATE account SET money = money + ? WHERE name = ?", money, toName);

                return true; // 正常返回，自动提交
            } catch (Exception e) {
                // 异常抛出，TransactionTemplate 捕获后会自动回滚
                throw new RuntimeException("转账失败，触发回滚", e);
            }
        });

        // 3. 事务外的操作（比如：发送转账成功的短信）
        System.out.println("--- 执行事务外的后置逻辑 ---");
    }
}
