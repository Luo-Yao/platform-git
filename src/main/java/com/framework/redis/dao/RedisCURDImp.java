package com.framework.redis.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-13 16:36
 * Email:johnny_lx@yahoo.com
 */
@Repository
public class RedisCURDImp extends AbstractRedisDaoBase{

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * Redis 新增
     * @param strKey
     * @param strValue
     */
    public boolean add(String strKey,String strValue){
        boolean result = (boolean) stringRedisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(strKey);
                byte[] name = serializer.serialize(strValue);
                return redisConnection.setNX(key, name);
            }
        });
        return result;
    }

    /**
     * Redis 删除
     * @param key
     */
    public void del(String key){
        stringRedisTemplate.delete(key);
    }

    /**
     * Redis 修改key
     * @param oldKey
     * @param newKey
     */
    public void updateKey(String oldKey,String newKey){
        stringRedisTemplate.rename(oldKey,newKey);
    }

    /**
     * Redis 根据key修改value
     * @param strKey
     */
    public boolean updateValue(String strKey,String value){
        if (get(strKey) == null) {
            throw new NullPointerException("数据行不存在, key = " + strKey);
        }
        boolean result = (boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            public Boolean doInRedis(RedisConnection redisConnection)
                    throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key  = serializer.serialize(strKey);
                byte[] name = serializer.serialize(value);
                redisConnection.set(key, name);
                return true;
            }
        });
        return result;
    }

    /**
     * Redis 根据key获取value
     * @param strKey
     */
    public String get(String strKey){
        String result = (String)redisTemplate.execute(new RedisCallback<String>() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(strKey);
                byte[] value = connection.get(key);
                if (value == null) {
                    return null;
                }
                String name = serializer.deserialize(value);
                return name;
            }
        });
        return result;
    }

    /**
     * 设置key的超时时间
     * @param strKey
     * @param seconds
     * @return
     */
    public boolean setKeyTimeOut(String strKey,long seconds){
        boolean result = (boolean)redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                RedisSerializer<String> serializer = getRedisSerializer();
                byte[] key = serializer.serialize(strKey);
                if(redisConnection.exists(key) == true) {
                    return redisConnection.expire(key, seconds);
                }else{
                    return false;
                }
            }
        });
        return result;
    }
}