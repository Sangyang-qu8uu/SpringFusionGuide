package org.example.spring.data.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching //开启事务性注解
public class SpringDataRedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataRedisApplication.class, args);
    }

}
