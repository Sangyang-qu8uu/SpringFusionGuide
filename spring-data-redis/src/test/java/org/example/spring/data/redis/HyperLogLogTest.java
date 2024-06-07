package org.example.spring.data.redis;

import jakarta.annotation.Resource;
import org.example.spring.data.redis.user.service.HyperLogLogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Description
 * @Author SangY
 * @Date 2024/6/7 21:50
 **/
@SpringBootTest
public class HyperLogLogTest {

    @Resource
    private HyperLogLogService hyperLogLogService;

    @BeforeEach
    public void setUp() {
        // 清除之前的测试数据
        hyperLogLogService.deleteAllUniqueUserKeys();
    }

    @Test
    public void testAddUserAndGetUniqueUserCount() {
        // 清理之前的数据
        LocalDate today = LocalDate.now();
        hyperLogLogService.getUniqueUserCountForDay(today);

        // 模拟用户访问
        hyperLogLogService.addUser("user1");
        hyperLogLogService.addUser("user2");
        hyperLogLogService.addUser("user1");  // 重复的用户ID，不会增加计数

        long uniqueUserCount = hyperLogLogService.getUniqueUserCountForDay(today);
        assertEquals(2, uniqueUserCount);
    }

    @Test
    public void testUniqueUserCountForDifferentDays() {
        LocalDate day1 = LocalDate.now().minusDays(1);
        LocalDate day2 = LocalDate.now().minusDays(2);

        // 清理之前的数据
        hyperLogLogService.getUniqueUserCountForDay(day1);
        hyperLogLogService.getUniqueUserCountForDay(day2);

        // 模拟不同天的用户访问
        hyperLogLogService.addUser("user1");
        hyperLogLogService.addUser("user3");

        long uniqueUserCountDay1 = hyperLogLogService.getUniqueUserCountForDay(day1);
        long uniqueUserCountDay2 = hyperLogLogService.getUniqueUserCountForDay(day2);

        assertEquals(0, uniqueUserCountDay1);
        assertEquals(0, uniqueUserCountDay2);
    }
}
