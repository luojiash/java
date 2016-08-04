package com.luojiash.test.aop;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.luojiash.test.annotation.LogAn;

@Aspect
public class LogAspect {

    @Pointcut("@annotation(com.luojiash.test.annotation.LogAn)")
    public void loggerAspect() {}

    // @Before(value = "loggerAspect()")
    @Before(value = "@annotation(logAn)")
    public void b(JoinPoint joinPoint, LogAn logAn) {
        System.out.println(logAn);
        System.out.println(joinPoint.getTarget().getClass().getSimpleName()+"#"+joinPoint.getSignature().getName());

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        if (method.getDeclaringClass().isInterface()) {
            try {
                method = joinPoint.getTarget().getClass()
                        .getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException | SecurityException e) {
                throw new RuntimeException(e);
            }
        }
        for (Annotation annotation : method.getAnnotations()) {
            System.out.println(annotation);
        }
        System.out.println("before");
    }
}
