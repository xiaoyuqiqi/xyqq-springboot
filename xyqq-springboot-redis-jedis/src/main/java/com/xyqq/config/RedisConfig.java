package com.xyqq.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RedisConfig {

    // disable表示如果未从配置文件中获取host，则默认值 为disable
    @Value("${spring.redis.host:disable}")
    private String host;

    @Value("${spring.redis.port:0}")
    private int port;

    @Value("${spring.redis.database:0}")
    private int database;

    @Value("${spring.redis.password:admin}")
    private String password;

    // 将获取的数据传入到initJedisPool方法中
    @Bean
    public RedisUtil getRedisUtil(){
        if ("disable".equals(host)){
            return  null;
        }
        RedisUtil redisUtil = new RedisUtil();
        // 调用initJedisPool方法将值传入
        redisUtil.initJedisPool(host,port,database,password);

        return redisUtil;
    }



}
