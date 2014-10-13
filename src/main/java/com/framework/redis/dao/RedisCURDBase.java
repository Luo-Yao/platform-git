package com.framework.redis.dao;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-12 17:07
 * Email:johnny_lx@yahoo.com
 */
public interface RedisCURDBase<T> {

    /**
     * 新增
     * @param o
     * @return
     */
    boolean add(T o);

    /**
     * 批量新增 使用pipeline方式
     * @param list
     * @return
     */
    boolean add(List<T> list);

    /**
     * 删除
     * @param key
     */
    void delete(String key);

    /**
     * 删除多个
     * @param keys
     */
    void detete(List<String> keys);

    /**
     * 修改
     * @param o
     * @return
     */
    boolean update(T o);

    /**
     * 通过key获取
     * @param keyId
     * @return
     */
    T get(String keyId);
}
