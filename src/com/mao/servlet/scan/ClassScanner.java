package com.mao.servlet.scan;

import com.mao.servlet.handler.WebRequestServlet;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 指定包扫描
 * @author mao by 14:42 2020/1/16
 */
public abstract class ClassScanner {

    /**
     * 获取到指定类之后的处理
     * 返回一个WebRequestServlet
     * 处理方法交给子类
     * @param clazz Class
     * @return WebRequestServlet
     */
    public abstract WebRequestServlet dealClass(Class<?> clazz);

    /**
     * 根据File扫描
     * 1.如果包为空，则返回空集合
     * 2.只获取File为class类型的文件
     * 3.遍历该File，如果是类，处理类，如果是文件夹，深度扫描
     * @param curFile 当前包File类
     * @param packName 当前包名称
     * @return WebRequestServlet集合
     */
    private List<WebRequestServlet> packetScanner(File curFile, String packName) throws ClassNotFoundException {
        List<WebRequestServlet> servlets = new ArrayList<>();
        //如果不是目录返回空集合
        if (!curFile.isDirectory()) {
            return servlets;
        }
        //扫描该包
        File[] files = curFile.listFiles();
        assert files != null;
        for(File file : files) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String fileName = file.getName().replace(".class", "");
                //去掉“.class”后就是文件名，路径名加文件名就是类名
                String className = packName + "." + fileName;
                Class<?> clazz = Class.forName(className);
                //只获取类
                if(clazz.isAnnotation()||clazz.isEnum()||clazz.isInterface()||clazz.isPrimitive())
                    continue;
                WebRequestServlet servlet = dealClass(clazz);
                if (null != servlet)
                    servlets.add(servlet);
            }else if(file.isDirectory()) {
                //如果该文件是目录就再一次调用此方法，将路径名加文件名（下一次路径）传过去
                List<WebRequestServlet> child = packetScanner(file,packName+"."+file.getName());
                servlets.addAll(child);
            }
        }
        return servlets;
    }

    /**
     * 包扫描
     * @param packetName 包路径名称
     * @return WebRequestServlet 集合
     * @throws ClassNotFoundException 如果找不到类则初始化失败
     */
    public List<WebRequestServlet> scanPacket(String packetName) throws Exception {
        List<WebRequestServlet> servlets = new ArrayList<>();
        //.转/
        String packetPath = packetName.replace(".", "/");
        //返回此Thread的上下文ClassLoader
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        //获取该包下资源
        Enumeration<URL> resources = classLoader.getResources(packetPath);
        while(resources.hasMoreElements()) {
            //这里得到的是该类的包路径
            URL url = resources.nextElement();
            //url.getProtocol()获取此url协议的名称,如果是文件输出file;如果是jar包，输出jar
            File file = new File(url.toURI());
            //如果这个文件不存在，就继续查找
            if (!file.exists()) continue;
            //如果是普通包就调用普通包扫描的方法
            List<WebRequestServlet> servlets1 = packetScanner(file, packetName);
            servlets.addAll(servlets1);
        }
        return servlets;
    }

}