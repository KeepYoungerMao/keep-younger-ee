package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * 模拟RequestMapping
 * @author mao by 16:07 2020/1/16
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServletMapping {
    String[] value() default {""};
}