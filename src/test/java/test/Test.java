package test;


import com.framework.common.inc.PlatformConstants;
import com.framework.common.util.MD5;
import com.framework.common.util.StringUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import java.io.UnsupportedEncodingException;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-27 15:16
 * Email:johnny_lx@yahoo.com
 */
public class Test {


    /**
     * 编码
     * @param cookieStr
     * @return
     */
    public static String encodeBase64(String cookieStr){

        try {
            cookieStr = new String(Base64.encode(cookieStr.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return cookieStr;
    }

    /**
     * 解码
     * @param cookieStr
     * @return
     */
    public static String decodeBase64(String cookieStr){
        try {
            cookieStr = new String(Base64.decode(cookieStr));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cookieStr;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String str = "ffdd订单";
        String decode = new String(Test.encodeBase64(str));
        System.out.println("eee----" + decode);

        String ddd = new String(Test.decodeBase64("OjMwMDAwMDE6MTQxMDkzMzY1MTQ0NToyNGEyYzE1MTg3YWE2ZTE5OWQ5NjRkMjJjYmIyNjdkNA=="));
        System.out.println("ddd----" + ddd);


        System.out.println("StringUtil.toMd5(\"123456\")==="+StringUtil.toMd5("123456"));
        System.out.println("System.currentTimeMillis()==="+System.currentTimeMillis());

        String s="OjE0MTA5Mjk4ODI5NDU6M2JhMTBkZWEzMDA1NThhMzQ1YmEwY2YzMmQ4NTk3YzY=";

        System.out.println("对比＝＝＝＝＝＝A---"+Test.decodeBase64(s));

        long a=System.currentTimeMillis()+1209600*1000;
        String y=StringUtil.toMd5("admin3000001"+String.valueOf(a));
        System.out.println("对比＝＝＝＝＝＝b---"+y);

        String cookieValueAfterDecode="admin,3000001,0:0:0:0:0:0:0:1,1410942612311,85c82d826c3fa6acefb609a30dbacbf2";
        String cookieValues[] = cookieValueAfterDecode.split(",");

        for(int x=0;x<cookieValues.length;x++){
            System.out.println(cookieValues[x]);
        }
    }
}
