package org.example.spring.data.redis.jedis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Component
public class JedisConnectionFactory {
    private static JedisPool jedisPool;

    @Autowired
    public JedisConnectionFactory(JedisPool jedisPool) {
        JedisConnectionFactory.jedisPool = jedisPool;
    }

    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    public static void closeJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }

    // 可选：添加关闭连接池的方法
    public static void closePool() {
        if (jedisPool != null) {
            jedisPool.close();
        }
    }

}
