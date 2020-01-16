package com.mao.servlet.handler;

import com.mao.servlet.scan.ClassHandlerMappingScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * http请求方法匹配器
 * @author mao by 11:18 2020/1/16
 */
public class HttpMethodHandlerMapping extends MethodHandlerMapping {

    private WebRequestServlet[] webRequestServlet;

    /**
     * 初始化方法
     * 扫描带有@Web注解的类和类中带有特定注解的方法并缓存
     * 注：没有做重复错误处理
     * @param packet 包扫描路径
     * @throws Exception 初始化异常
     */
    @Override
    public void init(String packet) throws Exception {
        long start = System.currentTimeMillis();
        System.out.println("init method start...");
        ClassHandlerMappingScanner scanner = new ClassHandlerMappingScanner();
        List<WebRequestServlet> servlets = scanner.scanPacket(packet);
        int size = servlets.size();
        if (size > 0){
            WebRequestServlet[] _servlets = new WebRequestServlet[size];
            for (int i = 0; i < size; i++) {
                WebRequestServlet servlet = servlets.get(i);
                System.out.println(servlet.getServlet().getName());
                _servlets[i] = servlet;
            }
            this.webRequestServlet = _servlets;
        } else {
            System.out.println("cannot find any web class in packet: "+packet);
        }
        long end = System.currentTimeMillis();
        System.out.println("init method end...["+(end-start)+"]");
    }

    @Override
    public void doServlet(HttpServletRequest request, HttpServletResponse response, HttpMethod httpMethod)
            throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        for (WebRequestServlet servlet : webRequestServlet) {
            for (WebRequestMethod method : servlet.getMethods()) {
                for (String s : method.getPattern()) {
                    String pattern = servlet+s;
                    if (requestURI.equals(pattern) && existHttpMethod(httpMethod,method.getMethodType())){
                        //invoke
                        try {
                            method.getMethod().invoke(servlet.getServlet(),request,response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setStatus(500);
                            response.getWriter().write("server error");
                        }
                    }
                }
            }
        }
        response.getWriter().write(requestURI+ " is not found");
    }


    private boolean existHttpMethod(HttpMethod method, HttpMethod[] methods){
        for (HttpMethod httpMethod : methods) {
            if (httpMethod == method)
                return true;
        }
        return false;
    }
}