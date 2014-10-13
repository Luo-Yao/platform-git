package com.framework.common.util;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-9-3 13:01
 * Email:johnny_lx@yahoo.com
 */
public class CookieUser implements Serializable {

    private static final long serialVersionUID = 4725479846692064140L;

    private Integer userId;

    private String userName;

    private String entrpriseId;

    private String host;

    private Long longTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntrpriseId() {
        return entrpriseId;
    }

    public void setEntrpriseId(String entrpriseId) {
        this.entrpriseId = entrpriseId;
    }

    public Long getLongTime() {
        return longTime;
    }

    public void setLongTime(Long longTime) {
        this.longTime = longTime;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
