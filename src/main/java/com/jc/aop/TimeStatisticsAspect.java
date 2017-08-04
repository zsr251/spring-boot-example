package com.jc.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 切面处理
 * Created by jasonzhu on 2017/8/2.
 */
@Aspect
@Component
public class TimeStatisticsAspect {
    Logger logger = LoggerFactory.getLogger(TimeStatisticsAspect.class);
    //当前时间
    public ThreadLocal<Map<String, Long>> threadLocalTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.jc.aop.TimeStatistics)")
    public void annotationPointCut() {
    }

    @Before("annotationPointCut()")
    public void before(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TimeStatistics timeStatistics = method.getAnnotation(TimeStatistics.class);
        long now = System.currentTimeMillis();
        Map<String, Long> timeMap = threadLocalTime.get();
        if (timeMap == null) {
            timeMap = new ConcurrentHashMap<>();
        }
        timeMap.put(method.getName(), now);
        threadLocalTime.set(timeMap);
        logger.debug("调用方法:{} 前拦截 方法开始时间:{}ms 其他:{}", method.getName(), System.currentTimeMillis(), timeStatistics.name());
    }

    @After("annotationPointCut()")
    public void after(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        TimeStatistics timeStatistics = method.getAnnotation(TimeStatistics.class);
        long begin = 0;
        Map<String, Long> timeMap = threadLocalTime.get();
        if (timeMap!=null){
            begin = timeMap.get(method.getName());
        }
        long now = System.currentTimeMillis();
        logger.debug("调用方法:{} 后拦截 方法开始时间:{}ms 方法结束时间:{}ms 方法耗时:{}ms 其他:{}", method.getName(), begin, now, now - begin, timeStatistics.name());
    }


}
