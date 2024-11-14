package com.xyqq.service.impl;

import com.xyqq.config.RedisUtil;
import com.xyqq.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService {


    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void set(String key, String value) {

        // 获取Jedis
        Jedis jedis = redisUtil.getJedis();
        jedis.set(key, value);

        // 如何获取name 的过期时间
        Long ttl = jedis.ttl(key);

        System.out.println(ttl);


        // 给key设置过期时间
//        jedis.expire(key,ttl.intValue());

        // 关闭redis
        jedis.close();



    }

    @Override
    public String get(String key) {
        Jedis jedis = redisUtil.getJedis();
        String value = jedis.get(key);
        jedis.close();
        return value;
    }
}
