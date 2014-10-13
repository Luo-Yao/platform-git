package com.framework.common.util;

import com.framework.common.inc.PlatformConstants;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-9-3 9:42
 * Email:johnny_lx@yahoo.com
 */
public class CookieUtil {

    private static Logger log= LoggerFactory.getLogger(CookieUtil.class);

    /**
     *保存Cookie到客户端
     *在座席登录、管理员登录中被调用
     *传递进来的user对象中封装了在登陆时填写的用户名与密码
     * @param user
     * @param response
     */
    public static void saveCookie(CookieUser user, HttpServletResponse response) {

        /**
         * cookie的有效期
         */
        long validTime = System.currentTimeMillis() + (Integer.valueOf(PlatformConstants.cookieMaxAge) * 1000);

        String setCookieValue = user.getUserName() + ","+ user.getEntrpriseId()+","+user.getHost()+","+ validTime + "," + PlatformConstants.webKey;

        log.debug("setCookieValue==================="+setCookieValue);
        /**
         * MD5加密用户详细信息
         */
        String cookieValueWithMd5 = StringUtil.toMd5(setCookieValue);
        /**
         * 将要被保存的完整的Cookie值
         */
        String cookieValue = user.getUserName() + ","+ user.getEntrpriseId()+ "," +StringUtil.toMd5(user.getHost())+","+ validTime + "," + cookieValueWithMd5;
        /**
         *再一次对Cookie的值进行BASE64编码
         */
        String cookieValueBase64 = new String(Base64.encode(cookieValue.getBytes()));
        /**
         * 开始保存Cookie
         */
        Cookie cookie = new Cookie(PlatformConstants.cookieDomainName, cookieValueBase64);
        /**
         * 存两年(这个值应该大于或等于validTime)60 * 60 * 24 * 365 * 2
         */
        cookie.setMaxAge(Integer.valueOf(PlatformConstants.cookieMaxAge));
        /**
         * cookie有效路径是网站根目录
         * 这个路径即该工程下都可以访问该cookie 如果不设置路径，那么只有设置该cookie路径及其子路径可以访问
         */
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        /**
         * 向客户端写入
         */
        response.addCookie(cookie);
    }

    /**
     * 获取Cookie
     * @param request
     * @return
     */
    public static String getCookie(HttpServletRequest request){
        Cookie cookies[] = request.getCookies();
        String cookieValue = null;
        if(cookies!=null){
            for(int i=0;i<cookies.length;i++){
                if (PlatformConstants.cookieDomainName.equals(cookies[i].getName())) {
                    cookieValue = cookies[i].getValue();
                    break;
                }
            }
         }
        //如果cookieValue为空,返回,
        if(cookieValue==null){
            return null;
        }
        log.debug("cookieValue==============="+cookieValue);
        return cookieValue;
    }

    /**
     * 验证Cookie
     * @param cookieValue
     * @param request
     * @param response
     * @return
     */
    public static String validateCookie(String cookieValue,HttpServletRequest request,HttpServletResponse response){

        if(null == cookieValue){
            return null;
        }else{
            String cookieValueAfterDecode =new String(Base64.decode(cookieValue));

            log.debug("cookieValueAfterDecode==========="+cookieValueAfterDecode);
            String cookieValues[] = cookieValueAfterDecode.split(",");
            for(int x=0;x<cookieValues.length;x++){
                log.debug("cookieValues[i]============="+cookieValues[x]);
            }
            log.debug("cookieValues.length="+cookieValues.length);
            if(cookieValues.length!=5) return "非法入侵，伪造的Cookie";
            log.debug("cookieValues1==============="+cookieValues[0].toString());
            log.debug("cookieValues2==============="+cookieValues[1].toString());
            log.debug("cookieValues3==============="+cookieValues[2].toString());
            log.debug("cookieValues4==============="+cookieValues[3].toString());
            log.debug("cookieValues5==============="+cookieValues[4].toString());
            /**
             * 判断是否已经过期
             */
            long validTimeInCookie = new Long(cookieValues[3]);
            if(validTimeInCookie<System.currentTimeMillis()){
                clearCookie(response);
                log.debug("你的Cookie已经失效,请重新登陆");
                return null;
            }
            /**
             * 判断IP是否为同一个
             */
            if(!cookieValues[2].equals(StringUtil.toMd5(request.getRemoteAddr()))){
                log.debug("你的IP发生变化,请重新登陆");
                return null;
            }
            //DB的判断，可以不做
        }


        return "success";
    }
    /**
     * 根据名字获取cookie
     * @param request
     * @param name cookie名字
     * @return
     */
    public static Cookie getCookieByName(HttpServletRequest request,String name){

        Map<String,Cookie> cookieMap = ReadCookieMap(request);
        if(cookieMap.containsKey(name)){
           Cookie cookie = (Cookie)cookieMap.get(name);
           return cookie;
        }else{
           return null;
        }
    }
    /**
     * 将cookie封装到Map里面
     * @param request
     * @return
     */
    private static Map<String,Cookie> ReadCookieMap(HttpServletRequest request){
        Map<String,Cookie> cookieMap = new HashMap<String,Cookie>();
        Cookie[] cookies = request.getCookies();
        if(null!=cookies){
            for(Cookie cookie : cookies){
                cookieMap.put(cookie.getName(), cookie);
            }
        }
        return cookieMap;
    }
    /**
     * 用户注销时,清除Cookie,在需要时可随时调用
     * @param response
     */
    public static void clearCookie( HttpServletResponse response){

        Cookie cookie = new Cookie(PlatformConstants.cookieDomainName, null);

        cookie.setMaxAge(0);

        cookie.setPath("/");

        response.addCookie(cookie);

    }
}
