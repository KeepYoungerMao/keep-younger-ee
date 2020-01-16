package com.mao.web;

import com.mao.servlet.annotation.GetMapping;
import com.mao.servlet.annotation.ServletMapping;
import com.mao.servlet.annotation.Web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试请求处理类
 * @author mao by 17:44 2020/1/16
 */
@Web
@ServletMapping("/v")
public class TestWeb {

    @GetMapping("/test.mao")
    public void test(HttpServletResponse response) throws IOException {
        response.getWriter().write("hello world");
    }

}