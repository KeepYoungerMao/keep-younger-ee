package com.mao.servlet.handler;

/**
 * 请求匹配管理器
 * 初次请求会实例化MethodHandlerMapping
 * 主要提供匹配器的实例化和提供匹配器的实例
 * @author mao by 10:54 2020/1/16
 */
public class MethodHandlerManager {

    private static boolean init = false;

    /** 匹配器 */
    private static MethodHandlerMapping methodHandlerMapping;

    /**
     * 初始化匹配器
     */
    public static <T extends MethodHandlerMapping> void initHandler(T mapping, String packet)
            throws Exception {
        if (!isInit()){
            methodHandlerMapping = mapping;
            methodHandlerMapping.init(packet);
            init = true;
        }
    }

    /**
     * 判断匹配器是否初始化
     */
    public static boolean isInit() {
        return init;
    }

    /**
     * 返回匹配器实例
     * @return MethodHandlerMapping
     */
    public static MethodHandlerMapping getInstance(){
        return methodHandlerMapping;
    }

}