package com.mao.util;

/**
 * 字符串操作工具类
 * @author mao by 15:46 2020/1/16
 */
public class SU {

    /**
     * 判断字符串是否为空
     * 仿写commons-lang3
     * @param str str
     * @return true/false
     */
    public static boolean isEmpty(String str){
        // " " is false
        return null == str || str.length() == 0;
    }

    /**
     * 判断字符串是否不为空
     * 仿写commons-lang3
     * @param str str
     * @return true/false
     */
    public static boolean isNotEmpty(String str){
        // " " is true
        return null != str && str.length() > 0;
    }

}