package com.luojiash.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m -Xms20m -Xmx20m
 * Java8的字符串常量不是放在Metaspace，而是在堆，最后的报错：
 * Exception in thread "main" java.lang.OutOfMemoryError: GC overhead limit exceeded
 */
public class RuntimeConstantPoolOOM {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        int i = 0;
        while (true) {
            list.add(String.valueOf(i++).intern());
        }
    }
}
