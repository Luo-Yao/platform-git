package com.framework.common.inc;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-25 13:20
 * Email:johnny_lx@yahoo.com
 */
public class PlatformConstants {

    public static final String DEFAULT_EMPTY_STR = "";
    /**
     * 定义apach-tomcat路径
     */
    public static String apacheTomcatPath;
    /**
     * 定义ckfinder上传路径
     */
    public static String ckinderUploadPath;

    /**
     * 保存cookie时的cookieName
     */
    public static String cookieDomainName;

    /**
     * 设置cookie有效期是两个星期，根据需要自定义
     */
    public static String cookieMaxAge;

    /**
     * 加密cookie时的平台自定码
     */
    public static String webKey;


    public void setApacheTomcatPath(String apacheTomcatPath) {
        PlatformConstants.apacheTomcatPath = apacheTomcatPath;
    }

    public void setCkinderUploadPath(String ckinderUploadPath) {
        PlatformConstants.ckinderUploadPath = ckinderUploadPath;
    }

    public void setCookieDomainName(String cookieDomainName) {
        PlatformConstants.cookieDomainName = cookieDomainName;
    }

    public void setCookieMaxAge(String cookieMaxAge) {
        PlatformConstants.cookieMaxAge = cookieMaxAge;
    }

    public void setWebKey(String webKey) {
        PlatformConstants.webKey = webKey;
    }
}
