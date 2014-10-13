package com.junit.mvc;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;
import org.springframework.web.servlet.mvc.annotation.DefaultAnnotationHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * JUnit测试action时使用的基类
 *
 * @version 1.0
 * @author: 罗尧
 * @since 14-8-9 21:17
 * Email:johnny_lx@yahoo.com
 */
@ContextConfiguration(locations = {"classpath*:/spring/applicationContext-core.xml"})
public class JUnitActionBase extends AbstractJUnit4SpringContextTests {

    @Autowired
    protected StringRedisTemplate template;

    @Autowired
    protected RedisTemplate redisTemplate;

    public  String getKey(String key){
        return template.opsForValue().get(key);
    }

    /**
     * 按key删除
     * @param key
     */
    public void delKey(String key){
         template.delete(key);

    }

    /**
     * 修改key
     * @param key
     * @param newKey
     */
    public void updateKey(String key,String newKey){
        template.rename(key,newKey);
    }


    @Test
    public void test(){
        String x= getKey("123");
        System.out.println("redis key is 123  -------"+x);

        delKey("123");
        System.out.println("redis key is 123  -------"+getKey("123")+"---------"+redisTemplate);
    }
}
