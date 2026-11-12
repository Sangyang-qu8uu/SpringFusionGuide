package com.example;

/**
 * Copyright (c) 2025 Your Company, Inc. All rights reserved.
 *
 * @author sang
 * @version 1.0
 * @description @description Lazy Initialization Singleton with Double-Checked Locking (DCL)
 * @since JDK 11
 */
public class DclSingle {

    private static volatile DclSingle dclSingle;

    private DclSingle() {
    }


    public static DclSingle getSingleTon() {
        if (null==dclSingle){
            synchronized (DclSingle.class){
                if (null==dclSingle){
                    dclSingle = new DclSingle();
                }
            }
        }
        return dclSingle;
    }
}
