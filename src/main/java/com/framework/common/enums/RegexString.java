package com.framework.common.enums;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-7-14
 * Time: 21:42
 * Email:johnny_lx@yahoo.com
 */
public class RegexString {

    //匹配一行
    public static String line = "[\\S ]+[\\S| ]*";
    //匹配非空的一行
    public static String lineTrim = "[\\S| ]+";
    //匹配整形
    public static String integer = "\\d+";
    //java注释
    public static String javaComment = "/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/";
    //字母
    public static String letter = "([a-z]|[A-Z])*";
    //数字字母或者下划线 ,以字母开头(java命名规范)
    public static String namingConvention = "^([a-z]|[A-Z])+([\\_]|\\d|[a-z]|[A-Z])*$";
    //匹配数字
    public static String number = "^\\d+[.]?\\d+$";

    /**
     * 包含某些字母的一行*
     */
    public static String getLine(String content) {
        return "[\\S ]*" + content + "[\\S| ]*";
    }
}
