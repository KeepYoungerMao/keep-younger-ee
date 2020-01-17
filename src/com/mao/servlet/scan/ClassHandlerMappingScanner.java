package com.mao.servlet.scan;

import com.mao.servlet.annotation.ServletMapping;
import com.mao.servlet.annotation.Web;
import com.mao.servlet.handler.WebRequestMethod;
import com.mao.servlet.handler.WebRequestServlet;
import com.mao.util.SU;

import java.util.List;

/**
 * 类扫描器
 * @author mao by 14:49 2020/1/16
 */
public class ClassHandlerMappingScanner extends ClassScanner {

    /**
     * 对扫描成功的类的处理
     * 收集具有特定注解的类
     * 对类中的方法继续扫描，并组合WebRequestServlet返回
     * @param clazz Class
     * @return WebRequestServlet
     */
    @Override
    public WebRequestServlet dealClass(Class<?> clazz) {
        Web web = clazz.getAnnotation(Web.class);
        if (null == web) return null;
        WebRequestServlet servlet = new WebRequestServlet();
        //设置路径
        ServletMapping servletMapping = clazz.getAnnotation(ServletMapping.class);
        if (null == servletMapping){
            servlet.setPattern("/");
        } else {
            String[] value = servletMapping.value();
            String _value = value[0];
            servlet.setPattern(transClassPattern(_value));
        }
        //设置类
        servlet.setServlet(clazz);
        //设置方法
        MethodHandlerMappingScanner scanner = new MethodHandlerMappingScanner();
        List<WebRequestMethod> methods = scanner.scanClass(clazz);
        int size = methods.size();
        if (size > 0){
            WebRequestMethod[] _methods = new WebRequestMethod[size];
            for (int i = 0; i < size; i++) {
                _methods[i] = methods.get(i);
            }
            servlet.setMethods(_methods);
        } else servlet.setMethods(null);
        return servlet;
    }

    /**
     * 路径补全
     * @param path 路径
     * @return 补全路径
     */
    private String transClassPattern(String path){
        if (SU.isNotEmpty(path))
            return path.startsWith("/") ? path : "/"+path;
        return "";
    }

}