package com.example;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description Eager Initialization Singleton (Thread-safe)
 * @since JDK 11
 */
public class EagerSingle {

    private static final EagerSingle EAGER_SINGLE =new EagerSingle();

    private EagerSingle() {
    }


    public static EagerSingle getSingleTon() {
        return EAGER_SINGLE;
    }
}
