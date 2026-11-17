package com.example.demo;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */

import com.example.demo.service.CacheDemoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    CommandLineRunner run(CacheDemoService cacheDemoService) {
        return args -> {
            cacheDemoService.demoFirstLevelCache();
            cacheDemoService.demoSecondLevelCache();
        };
    }
}
