package com.tinet.ccic.v3.app.demo.service.imp;

import com.framework.redis.dao.RedisCURDImp;
import com.tinet.ccic.v3.app.demo.dao.DemoUserMapper;
import com.tinet.ccic.v3.app.demo.dao.redis.DemoUserRedisDao;
import com.tinet.ccic.v3.app.demo.pojo.DemoUserPojo;
import com.tinet.ccic.v3.app.demo.service.DemoUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-8-6
 * Time: 17:21
 * Email:johnny_lx@yahoo.com
 */
@SuppressWarnings("ALL")
@Service    //声明Service组件    @Service("userService")
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
public class DemoUserServiceImp implements DemoUserService {

    @Autowired      //用于注入，(srping提供的) 默认按类型装配
    protected DemoUserMapper demoUserMapper;

    @Autowired
    private RedisCURDImp redisCURDImp;
    @Autowired
    private DemoUserRedisDao demoUserRedisDao;


    @Override
    public List getList(Map<String, Object> map) {

        List<DemoUserPojo> list = null;
        System.out.println("redis key '123' " + redisCURDImp.get("123"));

        DemoUserPojo demoUserPojo = new DemoUserPojo();
        demoUserPojo.setId(1);
        demoUserPojo.setName("Johnny");
        boolean res = demoUserRedisDao.add(demoUserPojo);
        if (res != true) {
            list = this.demoUserMapper.selectUsersList(map);
        }

        return list;
    }

    @Override
    public boolean addUser(DemoUserPojo demoUserPojo) {
        boolean res = demoUserMapper.insert(demoUserPojo);
        return res;
    }

    @Override
    public boolean delUser(Map map) {
        boolean res = demoUserMapper.del(map);
        return res;
    }

    @Override
    public boolean updateUser(DemoUserPojo demoUserPojo) {
        return false;
    }

    @Override
    public String printUser() {
        return null;
    }
}
