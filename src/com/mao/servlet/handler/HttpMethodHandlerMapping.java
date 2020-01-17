package com.mao.servlet.handler;

import com.mao.servlet.scan.ClassHandlerMappingScanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
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
        ClassHandlerMappingScanner scanner = new ClassHandlerMappingScanner();
        List<WebRequestServlet> servlets = scanner.scanPacket(packet);
        int size = servlets.size();
        if (size > 0){
            WebRequestServlet[] _servlets = new WebRequestServlet[size];
            for (int i = 0; i < size; i++)
                _servlets[i] = servlets.get(i);
            this.webRequestServlet = _servlets;
        } else
            System.out.println("cannot find any web class in packet: "+packet);
        long end = System.currentTimeMillis();
        System.out.println("init method success use ["+(end-start)+"]ms");
    }

    /**
     * 请求处理方法
     * 遍历缓存，匹配上对应路径和对应Http类型
     * 匹配不上则输出not found
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param httpMethod HttpMethod
     * @throws ServletException e
     * @throws IOException e
     */
    @Override
    public void doServlet(HttpServletRequest request, HttpServletResponse response,
                          HttpMethod httpMethod) throws ServletException, IOException {
        String requestURI = tranRequestURI(request);
        boolean unFind = true;
        for (WebRequestServlet servlet : webRequestServlet) {
            for (WebRequestMethod method : servlet.getMethods()) {
                for (String s : method.getPattern()) {
                    String pattern = servlet.getPattern()+s;
                    if (requestURI.equals(pattern) && existHttpMethod(httpMethod,method.getMethodType())){
                        //invoke
                        unFind = false;
                        try {
                            invoke(servlet.getServlet(),method.getMethod(),request,response);
                        } catch (Exception e) {
                            e.printStackTrace();
                            response.setStatus(500);
                            response.getWriter().write("server error");
                        }
                        break;
                    }
                }
            }
        }
        if (unFind)
            response.getWriter().write(requestURI+ " is not found");
    }

    /**
     * 实例化
     * 此处只传递HttpServletRequest和HttpServletResponse
     * 其他参数由request中获取
     * @param clazz 该方法所在类
     * @param method 该方法
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @throws Exception 实例化失败抛出异常
     */
    private void invoke(Class<?> clazz, Method method, HttpServletRequest request,
                        HttpServletResponse response) throws Exception {
        Parameter[] parameters = method.getParameters();
        int len = parameters.length;
        if (len <= 0){
            method.invoke(clazz.newInstance());
        } else {
            Object[] args = new Object[len];
            for (int i = 0; i < len; i++) {
                Class<?> type = parameters[i].getType();
                if (type.equals(HttpServletRequest.class))
                    args[i] = request;
                else if (type.equals(HttpServletResponse.class))
                    args[i] = response;
                else
                    args[i] = null;
            }
            method.invoke(clazz.newInstance(),args);
        }
    }

    /**
     * 获取请求uri
     * 剔除后缀.mao
     * @param request HttpServletRequest
     * @return uri
     */
    private String tranRequestURI(HttpServletRequest request){
        return request.getRequestURI().replaceAll(".mao","");
    }

    /**
     * 检查是否存在该请求类型
     * @param method 该方法
     * @param methods 请求类型
     * @return boolean
     */
    private boolean existHttpMethod(HttpMethod method, HttpMethod[] methods){
        for (HttpMethod httpMethod : methods) {
            if (httpMethod == method)
                return true;
        }
        return false;
    }
}