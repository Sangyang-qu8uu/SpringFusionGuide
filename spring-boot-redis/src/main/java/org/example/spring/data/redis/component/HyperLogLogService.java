package org.example.spring.data.redis.component;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

/**
 * @Description HyperLogLog在统计日活跃用户数（DAU，Daily Active Users）上的优势。
 * HyperLogLog在这种场景下非常有用，因为它能够在大规模用户数据中高效地估算唯一用户数量，而只占用很小的内存
 * @Author SangY
 * @Date 2024/6/7 21:48
 **/
@Component
public class HyperLogLogService {
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 我们将模拟一个简单的Web应用，统计每天的活跃用户数。具体来说，我们会：
     * 每天记录访问的用户ID。
     * 使用HyperLogLog来统计每天的独立用户数。
     * 提供一个获取某天独立用户数的方法。
     */

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public void addUser(String userId) {
        String key = getKeyForToday();
        redisTemplate.opsForHyperLogLog().add(key, userId);
    }

    public long getUniqueUserCountForDay(LocalDate date) {
        String key = getKeyForDate(date);
        return redisTemplate.opsForHyperLogLog().size(key);
    }

    private String getKeyForToday() {
        return getKeyForDate(LocalDate.now());
    }

    private String getKeyForDate(LocalDate date) {
        return "unique_users:" + date.format(formatter);
    }

    public void deleteAllUniqueUserKeys() {
        Set<String> keys = redisTemplate.keys("unique_users:*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
