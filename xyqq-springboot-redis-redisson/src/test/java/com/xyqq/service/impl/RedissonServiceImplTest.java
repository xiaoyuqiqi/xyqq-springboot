package com.xyqq.service.impl;

import com.xyqq.XyqqSpringbootRedisRedissonApplicationTests;
import com.xyqq.service.RedissonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class RedissonServiceImplTest extends XyqqSpringbootRedisRedissonApplicationTests {

    @Autowired
    private RedissonService redissonService;

    @Test
    void testLock() {

        redissonService.testLock();
    }
}