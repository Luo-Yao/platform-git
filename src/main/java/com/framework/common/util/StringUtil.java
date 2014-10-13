package com.framework.common.util;

import com.framework.common.enums.RegexString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-7-14
 * Time: 21:38
 * Email:johnny_lx@yahoo.com
 */
public class StringUtil {

    private static MD5 md5 = new MD5();


    /**
     * 重写commons-lang的StringUtils method
     */
    public static boolean contains(String str, String searchStr)
    {
        if ((str == null) || (searchStr == null)) {
            return false;
        }
        return str.indexOf(searchStr) >= 0;
    }

    /** 将首字母转为大写，其他不变 */
    public static String firstUp(String str) {
        char ch[];
        ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        String newString = new String(ch);
        return newString;
    }

    /** 将首字母转为小写，其他不变 */
    public static String firstLower(String str) {
        String first = str.substring(0, 1);
        String orther = str.substring(1);
        return first.toLowerCase() + orther;
    }

    /**
     * 通过get或set方法名获得字段名称
     *
     * @param methodName 方法名称
     */
    public static String getFieldName(String methodName) {
        return firstLower(methodName.substring(3));
    }

    /** 将符合数据库的命名转为java的命名 */
    public static String pareseUnderline(String code) {
        String[] strs = code.split("_");
        String first = strs[0].toLowerCase();
        if (strs.length == 1) {
            return first;
        }
        StringBuffer sb = new StringBuffer(first);
        for (int i = 1; i < strs.length; i++) {
            sb.append(firstUpOnly(strs[i]));
        }
        return sb.toString();
    }

    /** 将首字母转为大写，其他变小写 */
    public static String firstUpOnly(String str) {
        String first = str.substring(0, 1);
        String orther = str.substring(1);
        return first.toUpperCase() + orther.toLowerCase();
    }

    /**将大写字母转换为小写字母,并加上分隔符 */
    public static String pareseUpCase(String code,String replace) {
        char[] old = code.toCharArray();
        char[] news = code.toLowerCase().toCharArray();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < old.length; i++) {
            if (old[i] == news[i]) {
                sb.append(news[i]);
            } else {
                sb.append(replace + news[i]);
            }
        }
        return sb.toString();
    }


    /** 将符合java的命名转为数据库的命名 */
    public static String pareseUpCase(String code) {
        return pareseUpCase(code,"_");
    }

    public static void addLine(StringBuffer sb, String str) {
        sb.append(str);
        sb.append(System.getProperty("line.separator"));
    }

    /** 将字符串转为uicode */
    public static String getUnicode(String str) {
        if (str == null) {
            return "";
        }
        char[] chars = str.toCharArray();
        StringBuffer sb = new StringBuffer();
        for (char ch : chars) {
            String temp = Integer.toHexString(ch);
            if (temp.length() == 1) {
                temp = "000" + temp;
            }
            if (temp.length() == 2) {
                temp = "00" + temp;
            }
            if (temp.length() == 3) {
                temp = "0" + temp;
            }
            sb.append("\\u" + temp);
        }
        return sb.toString();
    }

    /**
     * 将字符串转为map 字符串:userName=张三,userCode=zhangsan,age 结果:map.put(userName,张三);
     * map.put(userCode,zhangsan); map.put(age,null);
     */
    public static Map toMap(String str) {
        return toMap(str, ",");
    }

    public static Map toMap(String str, String splitString) {
        Map map = new HashMap();
        if (str == null || str.equals("")) {
            return map;
        }
        String values[] = str.split(splitString);
        for (int i = 0; i < values.length; i++) {
            String tempValue = values[i];
            int pos = tempValue.indexOf("=");
            String key = "";
            String value = "";
            if (pos > -1) {
                key = tempValue.substring(0, pos);
                value = tempValue.substring(pos + splitString.length());
            } else {
                key = tempValue;
            }
            map.put(key, value);
        }

        return map;
    }

    public static Map toMap(List<String> strs) {
        Map map = new HashMap();
        if (strs == null) {
            return map;
        }
        for (String st : strs) {
            map.put(st, null);
        }
        return map;
    }

    public static List<String> getIds(List<String> pageId, String prefix) {
        List list = new ArrayList();
        if (pageId == null) {
            return list;
        }
        for (String id : pageId) {
            list.add(id);
        }
        return list;
    }

    /** 判断对象是否为空 */
    public static boolean isNull(Object object) {
        if (object instanceof String) {
            return isNull((String) object);
        }
        return object == null;
    }

    public static String getPropertyName(String methodName) {
        String propertyName = null;
        if (methodName.startsWith("get")) {
            propertyName = methodName.substring("get".length());
        } else if (methodName.startsWith("is")) {
            propertyName = methodName.substring("is".length());
        } else if (methodName.startsWith("set")) {
            propertyName = methodName.substring("set".length());
        }
        if (propertyName == null || propertyName.length() == 0) {
            return null;
        }

        return StringUtil.firstLower(propertyName);
    }

    /** 判断字符串是否为空 */
    public static boolean isNull(String value) {
        return value == null || value.equals("");
    }

    /** 判断字符串是否为空 */
    public static boolean isNotNull(String value) {
        return value != null &&! value.equals("");
    }

    /**
     * 获得指定字符串转化为Bytes数组后,数组的长度. <br>
     * <br>
     * <b>示例: </b> <br>
     * StringUtils.getBytesLength(&quot;中国人&quot;) 返回 6
     * StringUtils.getBytesLength(&quot;Cmm&quot;) 返回 3
     * StringUtils.getBytesLength(&quot;&quot;) 返回 0
     * StringUtils.getBytesLength(null) 返回 -1
     *
     * @param str 指定的字符串,字符串的值不能为null
     * @return 指定字符串转化为Bytes数组后,数组的长度
     * @throws
     */
    public static int getBytesLength(String str) {
        int length = 0;
        if (str == null) {
            length = -1;
        } else {
            try {
                length = str.getBytes("UTF-8").length;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return length;
    }

    public static String toMd5(String str) {
        return md5.getMD5ofStr(str);
    }

    /**判断一个字符串是否是数字,可以包含小数点*/
    public static boolean isNum(String str) {

        return (str.replaceAll("^\\d+[.]?\\d+$", "").length() == 0);

    };

    /**
     * 截取中文字符串
     *
     * @param oldString
     * @param length
     * @return
     */

    public static String subChineseStr(String oldString, int length) {
        int len = 0;
        int lenZh = 0;
        if (oldString == null) {
            return null;
        }
        if ("".equals(oldString)) {
            return "";
        }

        char c;
        for (int i = 0; i < oldString.length(); i++) {
            c = oldString.charAt(i);
            if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
                // 字母, 数字
                len++;
            } else {
                if (Character.isLetter(c)) { // 中文
                    len += 4;
                } else { // 符号或控制字符
                    len++;
                }
            }
            if (len <= length) {
                lenZh += 1; //用来SUBSTRING
            }
        }
        if (len > 0 && len <= length) {
            return oldString;
        } else if (len > length) {
            return oldString.substring(0, lenZh);
        }
        return null;
    }

    /**将字符串集合转换为字符串*/
    public static String toStirng(Collection<String> strs, String split) {
        StringBuffer sb = new StringBuffer();
        for (String st : strs) {
            sb.append(st + split);
        }
        return sb.substring(0, sb.length() - split.length());
    }

    public static String getLine(){
        return System.getProperty("line.separator");
    }

    public static  String appendStr(String source,String append,int no){
        StringBuilder sb=new StringBuilder(source);
        for(int i=0;i<no;i++){
            sb.append(append);
        }
        return sb.toString();
    }

    public static String toSqlString(String[] ids) {
        StringBuffer sb = new StringBuffer();
        for (String st : ids) {
            sb.append("'"+st+"'" + ",");
        }
        return sb.substring(0, sb.length() - 1);
    }

    /**将null转换为其他字符*/
    public static String NullToMessage(String str,String msg){
        if(str!=null){
            return str;
        }else{
            return msg;
        }
    }


    /**如果字符串是以某个字段串结尾的就截掉*/
    public static String getLastBefore(String str, String tail) {
        int index=str.lastIndexOf(tail);
        if(index!=-1){
            return str.substring(0,index);
        }else{
            return str;
        }
    }


    /**获得第一个字符以后的字符串*/
    public static String getFirstAfter(String str, String tail) {
        int index=str.indexOf(tail);
        if(index!=-1){
            return str.substring(index+tail.length());
        }else{
            return str;
        }
    }

    /**获得最有一个字符以后的字符串*/
    public static String getLastAfter(String str, String pos) {
        int index=str.lastIndexOf(pos);
        if(index!=-1){
            return str.substring(index+pos.length());
        }else{
            return str;
        }
    }


    /**截取特定字符以前的内容*/
    public static String getFirstBefore(String str, String pos) {
        int index=str.indexOf(pos);
        if(index!=-1){
            return str.substring(0,index);
        }else{
            return str;
        }
    }

    /**将某个字符串里的内容用正则替换
     * @param text 原来的内容
     * @param regx 正则表达式
     * @param before 替换后的内容前面部分
     * @param end 替换后的的内容后面部分
     * @param remain 是否保留原先正则里的内容
     * */
    public static String replace(String text,String regx,String before,String end,boolean remain){
        if(before==null){
            before="";
        }
        if(end==null){
            end="";
        }
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(text);
        String[] texts = text.split(regx);
        StringBuffer sb = new StringBuffer(texts[0]);
        int i = 1;
        while (matcher.find()) {
            String group=matcher.group();
            sb.append(before);
            if(remain){
                sb.append(group);
            }
            sb.append(end);
            sb.append(texts[i]);
            i++;
        }
        return sb.toString();
    }


    public static String pickUpFirst(String str,String regx){
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    /**在字符串前后追加
     * @param text 原来的内容
     * @param regx 正则表达式
     * @param before 前面追加的内容
     * @param end 后面追加的内容
     * */
    public static String append(String text,String regx,String before,String end){
        return StringUtil.replace(text, regx, before, end,true);
    }


    public static void main(String[] args) {
        System.out.println(StringUtil.isNamingConvention("111_aa"));
        System.out.println(StringUtil.isNamingConvention("abcd"));
        System.out.println(StringUtil.isNamingConvention("aa111a"));
        System.out.println(StringUtil.isNamingConvention("1aa_12312aaa"));
        System.out.println(StringUtil.isNamingConvention("aa___sfdsf_1bbb"));
        System.out.println(StringUtil.isNamingConvention("aa "));


    }

    /**创建一个字符串*/
    public static String createString(String str,int no,String split){
        if(no==0){
            return"";
        }
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<no;i++){
            sb.append(str+split);
        }
        return sb.substring(0,sb.length()-split.length());
    }

    /**判断一个字符串是否是数字*/
    public static boolean isInteger(String str){
        return str.replaceFirst(RegexString.integer, "").length()==0;
    }

    /**判断一个字符串是否是英文字符*/
    public static boolean isLetter(String str){
        return str.replaceFirst(RegexString.letter, "").length()==0;
    }

    /**判断是否符合命名规范,以字母开头,只包含字符数字或者下划线*/
    public static boolean isNamingConvention(String str){
        return str.replaceFirst(RegexString.namingConvention, "").length()==0;
    }
}
