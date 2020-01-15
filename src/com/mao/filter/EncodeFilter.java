package com.mao.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * 编码过滤器
 * @author mao by 11:54 2020/1/15
 */
public class EncodeFilter implements Filter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (null == encoding){
            String encoding = filterConfig.getInitParameter("encoding");
            System.out.println(encoding);
            this.encoding = encoding;
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        request.setCharacterEncoding(encoding);
        response.setCharacterEncoding(encoding);
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}