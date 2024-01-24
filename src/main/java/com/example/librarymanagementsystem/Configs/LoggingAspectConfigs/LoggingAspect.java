package com.example.librarymanagementsystem.Configs.LoggingAspectConfigs;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);


    @Around("execution(* com.example.librarymanagementsystem.Services.*.*(..))")
    public Object logMethodCall(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().toShortString();
        logger.info("Calling method: " + methodName);

        long startTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("Method execution time: " + (endTime - startTime) + " ms");
        return result;
    }

    @AfterThrowing(pointcut = "execution(* com.example.librarymanagementsystem.Services.*.*(..))", throwing = "ex")
    public void logException(Exception ex) {
        logger.error("Exception caught: " + ex.getMessage(), ex);
    }
}
