package com.mao.servlet.handler;

import java.lang.reflect.Method;

/**
 * 具体请求方法
 * @author mao by 14:58 2020/1/15
 */
public class WebRequestMethod {
    private String[] pattern;
    private Method method;
    private HttpMethod[] methodType;

    public String[] getPattern() {
        return pattern;
    }

    public void setPattern(String[] pattern) {
        this.pattern = pattern;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public HttpMethod[] getMethodType() {
        return methodType;
    }

    public void setMethodType(HttpMethod[] methodType) {
        this.methodType = methodType;
    }
}