package com.tinet.ccic.v3.app.demo.dao.redis;

import com.framework.redis.dao.AbstractRedisDaoBase;
import com.framework.redis.dao.RedisCURDBase;
import com.tinet.ccic.v3.app.demo.pojo.DemoUserPojo;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-12 17:22
 * Email:johnny_lx@yahoo.com
 */
@Service
public class DemoUserRedisDao extends AbstractRedisDaoBase<String, DemoUserPojo> implements RedisCURDBase<DemoUserPojo> {


    @Override
    public boolean add(DemoUserPojo demoUserPojo) {
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(String.valueOf(demoUserPojo.getId()));
                byte[] name = serializer.serialize(demoUserPojo.getName());
                return connection.setNX(key, name);
            }
        });
        return result;
    }

    @Override
    public boolean add(List<DemoUserPojo> list) {
        Assert.notEmpty(list);
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                for (DemoUserPojo demoUserPojo : list) {
                    byte[] key = serializer.serialize(String.valueOf(demoUserPojo.getId()));
                    byte[] name = serializer.serialize(demoUserPojo.getName());
                    connection.setNX(key, name);
                }
                return true;
            }
        }, false, true);
        return result;
    }

    @Override
    public void delete(String key) {
        List<String> list = new ArrayList<String>();
        list.add(key);
        redisTemplate.delete(list);
    }

    @Override
    public void detete(List keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public boolean update(DemoUserPojo demoUserPojo) {
        String key = String.valueOf(demoUserPojo.getId());
        if (get(key) == null) {
            throw new NullPointerException("数据行不存在, key = " + key);
        }
        boolean result = (boolean) redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection connection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(String.valueOf(demoUserPojo.getId()));
                byte[] name = serializer.serialize(demoUserPojo.getName());
                connection.set(key, name);
                return true;
            }
        });
        return result;
    }

    @Override
    public DemoUserPojo get(String keyId) {
        DemoUserPojo result = (DemoUserPojo) redisTemplate.execute(new RedisCallback<DemoUserPojo>() {
            public DemoUserPojo doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(keyId);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
                return new DemoUserPojo(Integer.valueOf(keyId), name, null, new Date());
            }
        });
        return result;
    }
}
