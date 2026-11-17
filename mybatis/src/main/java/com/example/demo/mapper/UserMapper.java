package com.example.demo.mapper;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */

import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    User selectById(@Param("id") Long id);
}
