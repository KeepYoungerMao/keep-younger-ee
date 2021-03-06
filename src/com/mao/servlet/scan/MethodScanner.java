package com.mao.servlet.scan;

import com.mao.servlet.handler.WebRequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 指定类扫描
 * @author mao by 14:39 2020/1/16
 */
public abstract class MethodScanner {

    /**
     * 处理找到的方法
     * @param method method
     * @return WebRequestMethod
     */
    public abstract WebRequestMethod dealMethod(Method method);

    /**
     * 扫描类，获取类中方法
     * @param clazz class
     * @return WebRequestMethod集合
     */
    List<WebRequestMethod> scanClass(Class<?> clazz){
        List<WebRequestMethod> methods = new ArrayList<>();
        Method[] _methods = clazz.getDeclaredMethods();
        if (_methods.length > 0){
            for (Method method : _methods) {
                WebRequestMethod webRequestMethod = dealMethod(method);
                methods.add(webRequestMethod);
            }
        }
        return methods;
    }

}