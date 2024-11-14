package com.xyqq.service.impl;

import com.xyqq.XyqqSpringbootRedisJedisApplicationTests;
import com.xyqq.service.RedisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RedisServiceImplTest extends XyqqSpringbootRedisJedisApplicationTests {

    @Autowired
    private RedisService redisService;

    @Test
    public void test()
    {
        redisService.set("name","xyqq");
        String name = redisService.get("name");
        System.out.println(name);
    }

}