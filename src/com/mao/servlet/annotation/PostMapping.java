package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * 模拟PostMapping
 * @author mao by 16:09 2020/1/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PostMapping {
    String[] value() default {""};
}