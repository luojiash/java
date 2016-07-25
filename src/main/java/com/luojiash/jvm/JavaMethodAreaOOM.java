package com.luojiash.jvm;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * -XX:MetaspaceSize=10m -XX:MaxMetaspaceSize=10m
 * 
 * java.lang.OutOfMemoryError: Metaspace
 */
public class JavaMethodAreaOOM {

    public static void main(String[] args) {
        while (true) {
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(OOMObject.class);
            // enhancer.setUseCache(true); // 只需加载一个类
            enhancer.setUseCache(false);
            enhancer.setCallback(new MethodInterceptor() {
                
                @Override
                public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                    return proxy.invokeSuper(obj, args);
                }
            });
            OOMObject object = (OOMObject) enhancer.create();
            System.out.println(object.getClass().getName());
        }
    }
    
    static class OOMObject {
        
    }
}
