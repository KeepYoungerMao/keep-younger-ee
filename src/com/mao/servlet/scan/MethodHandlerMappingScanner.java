package com.mao.servlet.scan;

import com.mao.servlet.annotation.*;
import com.mao.servlet.handler.HttpMethod;
import com.mao.servlet.handler.WebRequestMethod;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 方法扫描器
 * @author mao by 16:40 2020/1/16
 */
public class MethodHandlerMappingScanner extends MethodScanner {

    /**
     * 对扫描成功得到方法的处理
     * 收集具有特定注解的方法
     * 并组合WebRequestMethod
     * @param method method
     * @return WebRequestMethod
     */
    @Override
    public WebRequestMethod dealMethod(Method method) {
        List<Annotation> annotations = getAnnotations(method);
        if (annotations.size() > 1)
            throw new RuntimeException("method "+method.getName()+" at class "+
                    method.getDeclaringClass().getName()+" has Web Annotation more than two");
        if (annotations.size() > 0){
            Annotation annotation = annotations.get(0);
            WebRequestMethod _method = new WebRequestMethod();
            _method.setPattern(annotation.getPath());
            _method.setMethod(method);
            _method.setMethodType(annotation.getType());
            return _method;
        }
        return null;
    }

    /**
     * 获取可以用到的注解集合
     * 获取集合的原因是一个方法上不能同时使用2个或以上的web类型的注解
     * @param method method
     * @return 注解类型集合
     */
    private List<Annotation> getAnnotations(Method method){
        List<Annotation> list = new ArrayList<>();
        ServletMapping servletMapping = method.getAnnotation(ServletMapping.class);
        if (null != servletMapping)
            list.add(new Annotation(servletMapping.value(),HttpMethod.values()));
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        if (null != getMapping)
            list.add(new Annotation(getMapping.value(),new HttpMethod[]{HttpMethod.GET}));
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        if (null != postMapping)
            list.add(new Annotation(postMapping.value(),new HttpMethod[]{HttpMethod.POST}));
        PutMapping putMapping = method.getAnnotation(PutMapping.class);
        if (null != putMapping)
            list.add(new Annotation(putMapping.value(),new HttpMethod[]{HttpMethod.PUT}));
        DeleteMapping deleteMapping = method.getAnnotation(DeleteMapping.class);
        if (null != deleteMapping)
            list.add(new Annotation(deleteMapping.value(),new HttpMethod[]{HttpMethod.DELETE}));
        return list;
    }

}