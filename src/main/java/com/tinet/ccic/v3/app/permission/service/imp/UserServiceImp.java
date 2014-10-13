package com.tinet.ccic.v3.app.permission.service.imp;

import com.tinet.ccic.v3.app.permission.dao.UserMapper;
import com.tinet.ccic.v3.app.permission.pojo.UserPojo;
import com.tinet.ccic.v3.app.permission.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-10 20:45
 * Email:johnny_lx@yahoo.com
 */
@SuppressWarnings("ALL")
@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<UserPojo> userLogin(Map map) {
        List<UserPojo> list = this.userMapper.userLogin(map);
        return list;
    }
}
