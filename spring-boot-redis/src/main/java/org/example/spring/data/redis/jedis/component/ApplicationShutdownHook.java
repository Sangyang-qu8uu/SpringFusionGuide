package org.example.spring.data.redis.jedis.component;

import jakarta.annotation.PreDestroy;
import org.example.spring.data.redis.jedis.config.JedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * Spring Boot 应用程序的关闭，释放RedisPool资源
 */
@Component
public class ApplicationShutdownHook {

    @PreDestroy
    public void onShutdown() {
        JedisConnectionFactory.closePool();
    }
}