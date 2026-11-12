package com.example;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description @description Lazy Initialization Singleton with Double-Checked Locking (DCL)
 * @since JDK 11
 */
public class LazySingle {

    private static LazySingle lazySingle;

    private LazySingle() {
    }


    public static LazySingle getLazySingle() {
        if (null == lazySingle) {
            lazySingle = new LazySingle();
        }
        return lazySingle;
    }
}
