package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * 模拟GetMapping
 * @author mao by 16:08 2020/1/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GetMapping {
    String[] value() default {""};
}