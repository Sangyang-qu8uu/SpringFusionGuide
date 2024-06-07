package org.example.spring.data.redis;

import jakarta.annotation.Resource;
import org.example.spring.data.redis.service.RedisService;
import org.example.spring.data.redis.user.domian.User;
import org.example.spring.data.redis.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 */
@SpringBootTest
public class RedisGenerateTest {

    @Resource
    private RedisService redisService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void testGenerateId() {
        long id1 = redisService.generateId();
        long id2 = redisService.generateId();
        assertTrue(id2 > id1, "ID should be incremented");
    }

    @Test
    public void testGenerateIdWithTimestamp() {
        String id1 = redisService.generateIdWithTimestamp();
        String id2 = redisService.generateIdWithTimestamp();
        assertNotEquals(id1, id2, "IDs with timestamp should be unique");
    }

    @Test
    public void testResetUniqueId() {
        // Reset the unique_id key to simulate a fresh start
        redisTemplate.delete("unique_id");

        long id1 = redisService.generateId();
        assertEquals(1, id1, "The first ID should be 1 after reset");
    }
}