package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * 模拟PutMapping
 * @author mao by 16:10 2020/1/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PutMapping {
    String[] value() default {""};
}