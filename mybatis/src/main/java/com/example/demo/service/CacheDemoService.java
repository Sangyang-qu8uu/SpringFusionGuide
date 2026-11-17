package com.example.demo.service;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */

import com.example.demo.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.stereotype.Service;

@Service
public class CacheDemoService {

    private final SqlSessionFactory sqlSessionFactory;

    public CacheDemoService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    /** 一级缓存演示：同一个 SqlSession 内连续查询 */
    public void demoFirstLevelCache() {
        System.out.println("\n========== 1. 一级缓存演示（同一个 SqlSession）==========");
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            System.out.println(">>> 第一次查询 id=1");
            System.out.println(mapper.selectById(1L));
            System.out.println(">>> 第二次查询 id=1（同 session）");
            System.out.println(mapper.selectById(1L));
            System.out.println(">>> 第三次查询 id=1（同 session）");
            System.out.println(mapper.selectById(1L));
        }
    }

    /** 二级缓存演示：不同 SqlSession 之间共享 */
    public void demoSecondLevelCache() {
        System.out.println("\n========== 2. 二级缓存演示（不同 SqlSession）==========");

        // session1 查询并提交，数据写入二级缓存
        try (SqlSession session1 = sqlSessionFactory.openSession()) {
            UserMapper mapper1 = session1.getMapper(UserMapper.class);
            System.out.println(">>> session1 查询 id=2");
            System.out.println(mapper1.selectById(2L));
            session1.commit(); // 必须 commit 或 close，二级缓存才生效
        }

        // session2 新会话查询，应命中二级缓存
        try (SqlSession session2 = sqlSessionFactory.openSession()) {
            UserMapper mapper2 = session2.getMapper(UserMapper.class);
            System.out.println(">>> session2 查询 id=2（新 session）");
            System.out.println(mapper2.selectById(2L));
        }

        // session3 再查，继续命中二级缓存
        try (SqlSession session3 = sqlSessionFactory.openSession()) {
            UserMapper mapper3 = session3.getMapper(UserMapper.class);
            System.out.println(">>> session3 查询 id=2（新 session）");
            System.out.println(mapper3.selectById(2L));
        }
    }
}
