package com.luojiash.test.aop;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;

import com.alibaba.fastjson.JSON;

@Aspect
public class StudentAOP {

    @AfterReturning(returning="object",pointcut="execution(* com.luojiash.test.service.*.*(..))")
    public void r(Object object) {
        System.out.println("return "+JSON.toJSONString(object));
    }
}
