package com.project_REST.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.project_REST.service.*.*(..))")
    public void log(JoinPoint jp) {
        System.out.println("Calling: " + jp.getSignature());
    }
}
