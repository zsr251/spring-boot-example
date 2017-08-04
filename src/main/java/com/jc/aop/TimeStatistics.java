package com.jc.aop;

import java.lang.annotation.*;

/**
 * 时间统计
 * Created by jasonzhu on 2017/8/2.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeStatistics {
    String name() default "";
}
