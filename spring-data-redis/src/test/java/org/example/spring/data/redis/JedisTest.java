package org.example.spring.data.redis;

import org.example.spring.data.redis.jedis.config.JedisConnectionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;

@SpringBootTest
class JedisTest {
    Jedis jedis;

    @BeforeEach
    void setUp() {
        //建立连接
        jedis = JedisConnectionFactory.getJedis();
        //选择库
        jedis.select(0);
    }

    @AfterEach
    void tearDown() {
        JedisConnectionFactory.closeJedis(jedis);
    }

    @Test
    void contextLoads() {
        // 执行具体的操作
        jedis.set("username", "lisi");
        String value = jedis.get("username");
        System.out.println(value);
    }
}
