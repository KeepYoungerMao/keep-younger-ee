package com.mao.web;

import com.mao.servlet.annotation.GetMapping;
import com.mao.servlet.annotation.ServletMapping;
import com.mao.servlet.annotation.Web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 测试请求处理类
 * @author mao by 17:44 2020/1/16
 */
@Web
@ServletMapping("v")
public class TestWeb {

    /**
     * 测试请求
     * 测试请求转发
     */
    @GetMapping("test")
    public void test(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setAttribute("result","test success!");
        request.getRequestDispatcher("/template/test.jsp").forward(request,response);
    }

}