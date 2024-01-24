package com.example.librarymanagementsystem.Configs.CachingConfigs;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

@Aspect
@Component
public class CachingAspect {

    @Autowired
    private CacheManager cacheManager;

    @Around("@annotation(org.springframework.cache.annotation.Cacheable)")
    public Object cache(ProceedingJoinPoint joinPoint) throws Throwable {
        // Build a unique cache key based on method name and parameters
        String cacheKey = buildCacheKey(joinPoint.getSignature().getName(), joinPoint.getArgs());

        // Try to get the result from the cache
        Cache cache = cacheManager.getCache(getCacheName(joinPoint));
        assert cache != null;
        Cache.ValueWrapper valueWrapper = cache.get(cacheKey);
        if (valueWrapper != null) {
            return valueWrapper.get();
        }

        // Proceed with the actual method invocation
        Object result = joinPoint.proceed();

        // Put the result in the cache
        cache.put(cacheKey, result);

        return result;
    }

    private String buildCacheKey(String methodName, Object[] args) {
        // Build a unique cache key based on method name and parameters
        return methodName + Arrays.hashCode(args);
    }

    private String getCacheName(ProceedingJoinPoint joinPoint) {
        // Extract the cache name from the annotation
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Cacheable cacheableAnnotation = method.getAnnotation(Cacheable.class);
        return cacheableAnnotation.value()[0];
    }
}
