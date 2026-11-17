package com.example.demo.entity;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description
 * @since JDK 11
 */

import java.io.Serializable;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Integer age;

    // getter/setter 省略，用 Lombok 也行
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Integer getAge() { return age; }
    public void setAge(Integer age) { this.age = age; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', age=" + age + "}";
    }
}
