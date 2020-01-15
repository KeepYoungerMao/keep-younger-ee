package com.mao.servlet.handler;

import javax.servlet.http.HttpServlet;

/**
 * @author mao by 15:20 2020/1/15
 */
public class MethodHandlerMapping extends HttpServlet {

    private WebRequestServlet webRequestServlet;

    public MethodHandlerMapping() {
        initHandler();
    }

    public void initHandler(){
        if (null == webRequestServlet)
            this.webRequestServlet = new WebRequestServlet();
    }

}