package com.luojiash.jvm;

import java.lang.reflect.Field;
import sun.misc.Unsafe;

/**
 * -XX:MaxDirectMemorySize=10m -Xms20m -Xmx20m
 *
 */
public class DirectMemoryOOM {

    private static final int _1M=1024 * 1024;
    
    public static void main(String[] args) throws Exception {
        Field unsafeField = Unsafe.class.getDeclaredFields()[0];
        unsafeField.setAccessible(true);
        Unsafe unsafe = (Unsafe) unsafeField.get(null);
        while (true) {
            unsafe.allocateMemory(_1M);
        }
    }
    /*
     * Exception in thread "main" java.lang.OutOfMemoryError
            at sun.misc.Unsafe.allocateMemory(Native Method)
            at test.DirectMemoryOOM.main(DirectMemoryOOM.java:15)

     */
}
