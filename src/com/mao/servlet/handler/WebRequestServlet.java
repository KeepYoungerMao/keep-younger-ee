package com.mao.servlet.handler;

/**
 * web请求方法缓存
 * @author mao by 14:57 2020/1/15
 */
public class WebRequestServlet {
    private String pattern;
    private Class<?> servlet;
    private WebRequestMethod[] methods;

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    public Class<?> getServlet() {
        return servlet;
    }

    public void setServlet(Class<?> servlet) {
        this.servlet = servlet;
    }

    public WebRequestMethod[] getMethods() {
        return methods;
    }

    public void setMethods(WebRequestMethod[] methods) {
        this.methods = methods;
    }
}