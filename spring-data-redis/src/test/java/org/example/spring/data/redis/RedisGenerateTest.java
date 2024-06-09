package org.example.spring.data.redis;

import jakarta.annotation.Resource;
import org.example.spring.data.redis.utils.RedisUtils;
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
    private RedisUtils redisUtils;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Test
    public void testGenerateId() {
        long id1 = redisUtils.generateId();
        long id2 = redisUtils.generateId();
        assertTrue(id2 > id1, "ID should be incremented");
    }

    @Test
    public void testGenerateIdWithTimestamp() {
        String id1 = redisUtils.generateIdWithTimestamp();
        String id2 = redisUtils.generateIdWithTimestamp();
        assertNotEquals(id1, id2, "IDs with timestamp should be unique");
    }

    @Test
    public void testResetUniqueId() {
        // Reset the unique_id key to simulate a fresh start
        redisTemplate.delete("unique_id");

        long id1 = redisUtils.generateId();
        assertEquals(1, id1, "The first ID should be 1 after reset");
    }
}