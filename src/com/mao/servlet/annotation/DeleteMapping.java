package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * 模拟DeleteMapping
 * @author mao by 16:11 2020/1/16
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeleteMapping {
    String[] value() default {""};
}