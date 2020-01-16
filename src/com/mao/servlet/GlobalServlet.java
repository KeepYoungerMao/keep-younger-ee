package com.mao.servlet;

import com.mao.servlet.handler.HttpMethod;
import com.mao.servlet.handler.MethodHandlerManager;
import com.mao.servlet.handler.MethodHandlerMapping;
import com.mao.util.SU;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 全局请求Servlet
 * @author mao by 11:35 2020/1/15
 */
public class GlobalServlet extends HttpServlet {

    private static final String METHOD_HANDLER_MAPPING = "MethodHandlerMapping";

    private static final String PACKET_SCAN = "PacketScan";

    @Override
    public void init(ServletConfig config) throws ServletException {
        if (!MethodHandlerManager.isInit()){
            String name = config.getServletContext().getInitParameter(METHOD_HANDLER_MAPPING);
            String packet = config.getServletContext().getInitParameter(PACKET_SCAN);
            if (SU.isEmpty(name))
                throw new RuntimeException("please init param: "+METHOD_HANDLER_MAPPING);
            if (SU.isEmpty(packet))
                throw new RuntimeException("please init param: "+PACKET_SCAN);
            Class mapping;
            try {
                mapping = Class.forName(name);
            } catch (ClassNotFoundException e) {
                mapping = null;
                e.printStackTrace();
            }
            if (null != mapping)
                try {
                    MethodHandlerManager.initHandler((MethodHandlerMapping) mapping.newInstance(),packet);
                } catch (Exception e) {
                    throw new RuntimeException("cannot init class "+name+".");
                }
            else throw new RuntimeException("cannot init class "+name+".");
        }
    }

    @Override
    protected void doHead(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.HEAD);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.OPTIONS);
    }

    @Override
    protected void doTrace(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.TRACE);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.GET);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.PUT);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response,HttpMethod.DELETE);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        MethodHandlerManager.getInstance().doServlet(request,response, HttpMethod.POST);
    }

}