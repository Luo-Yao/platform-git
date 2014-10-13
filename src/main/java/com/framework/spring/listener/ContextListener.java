package com.framework.spring.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;

/**
 * Created with IntelliJ IDEA.
 * 系统启动监听器,初始化spring工厂,freemarker工厂,系统常量
 * User: LuoYao
 * Date: 14-7-14
 * Time: 20:08
 * Email:johnny_lx@yahoo.com
 */
public class ContextListener extends PlatformContextListener {

    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);

    public void contextInitialized(ServletContextEvent event) {
        log.info("系统开始启动...");
        long start = System.currentTimeMillis();
        super.contextInitialized(event);

        log.info("开始加载常量");
        //.....

        log.info("开始装载系统配置");
        //.....

        log.info("其他初始化操作");
        //.....

        long end = System.currentTimeMillis();
        long consume = end - start;
        long min = consume / 1000 / 60;
        long sec = (consume / 1000) % 60;


        if (min != 0) {
            log.info("----------------系统已经正常启动,共耗时:" + consume + "毫秒,约合" + min + "分钟" + sec + "秒------------------");
        } else {
            log.info("----------------系统已经正常启动,共耗时:" + consume + "毫秒,约合" + sec + "秒-----------------------");
        }
    }
}
