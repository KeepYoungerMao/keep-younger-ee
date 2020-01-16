package com.mao.servlet.annotation;

import com.mao.servlet.handler.HttpMethod;

/**
 * @author mao by 16:52 2020/1/16
 */
public class Annotation {

    private String[] path;
    private HttpMethod[] type;

    public Annotation(String[] path, HttpMethod[] type){
        this.path = path;
        this.type = type;
    }

    public String[] getPath() {
        return path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public HttpMethod[] getType() {
        return type;
    }

    public void setType(HttpMethod[] type) {
        this.type = type;
    }
}