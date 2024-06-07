package org.example.spring.data.redis.user.service;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * 统计用户活跃情况，如每日活跃用户
 * 实现布隆过滤器
 * 数据分析中的位操作
 * @Description
 * @Author SangY
 * @Date 2024/6/7 12:59
 **/
@Service
public class BitmapService {

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    // 设置某天某用户是否活跃
    public void setUserActive(String userId, String date) {
        redisTemplate.opsForValue().setBit("user:active:" + date, Long.parseLong(userId), true);
    }

    // 检查某天某用户是否活跃
    public boolean isUserActive(String userId, String date) {
        return redisTemplate.opsForValue().getBit("user:active:" + date, Long.parseLong(userId));
    }

    // 统计某天活跃用户数
    public long countActiveUsers(String date) {
        String key = "user:active:" + date;
        return redisTemplate.execute((RedisCallback<Long>) connection -> connection.bitCount(key.getBytes()));
    }

    // 批量设置用户活跃状态
    public void setUsersActive(String date, String... userIds) {
        for (String userId : userIds) {
            setUserActive(userId, date);
        }
    }

    // 批量检查用户活跃状态
    public boolean[] areUsersActive(String date, String... userIds) {
        boolean[] activeStatus = new boolean[userIds.length];
        for (int i = 0; i < userIds.length; i++) {
            activeStatus[i] = isUserActive(userIds[i], date);
        }
        return activeStatus;
    }
}