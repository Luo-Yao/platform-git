package com.tinet.ccic.v3.app.demo.service;

import com.tinet.ccic.v3.app.demo.pojo.DemoUserPojo;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-8-6
 * Time: 17:41
 * Email:johnny_lx@yahoo.com
 */
public interface DemoUserService {

    /**
     * 获取user列表
     *
     * @return
     */
    public List getList(Map<String, Object> map);

    public String printUser();

    public boolean addUser(DemoUserPojo demoUserPojo);

    public boolean delUser(Map map);

    public boolean updateUser(DemoUserPojo demoUserPojo);
}
