package com.tinet.ccic.v3.app.permission.dao;

import com.tinet.ccic.v3.app.permission.pojo.UserPojo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-10 20:05
 * Email:johnny_lx@yahoo.com
 */

public interface UserMapper {

    List<UserPojo> userLogin(Map<String, Object> map);
}
