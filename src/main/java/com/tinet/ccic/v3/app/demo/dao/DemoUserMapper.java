package com.tinet.ccic.v3.app.demo.dao;

import com.tinet.ccic.v3.app.demo.pojo.DemoUserPojo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: LuoYao
 * Date: 14-8-6
 * Time: 17:19
 * Email:johnny_lx@yahoo.com
 */
@Repository
public interface DemoUserMapper {

    /**
     * 查询  用户清单
     */
    List<DemoUserPojo> selectUsersList(Map<String, Object> map);

    /**
     * 新增一个用户
     *
     * @param demoUserPojo
     * @return
     */
    Boolean insert(DemoUserPojo demoUserPojo);

    /**
     * 删除一个用户
     *
     * @param map
     * @return
     */
    Boolean del(Map<String, Object> map);

    /**
     * 更新用户信息
     *
     * @param demoUserPojo
     * @return
     */
    Boolean update(DemoUserPojo demoUserPojo);
}
