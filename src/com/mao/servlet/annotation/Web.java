package com.mao.servlet.annotation;

import java.lang.annotation.*;

/**
 * Web注解，有此注解表示为一个请求类
 * 模拟@Controller
 * @author mao by 16:04 2020/1/16
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Web {
}