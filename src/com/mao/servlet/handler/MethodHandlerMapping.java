package com.mao.servlet.handler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求方法匹配器
 * @author mao by 15:20 2020/1/15
 */
public abstract class MethodHandlerMapping {

    /**
     * 初始化一些参数
     */
    public abstract void init(String packet) throws Exception;

    /**
     * 请求的处理
     * 此处不做处理，具体处理方式取决于字类。
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param httpMethod HttpMethod
     * @throws ServletException e
     * @throws IOException e
     */
    public abstract void doServlet(HttpServletRequest request, HttpServletResponse response,
                                   HttpMethod httpMethod) throws ServletException, IOException;

}