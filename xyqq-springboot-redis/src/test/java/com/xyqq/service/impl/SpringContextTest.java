package com.xyqq.service.impl;

import com.xyqq.XyqqSpringbootRedisApplicationTests;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SpringContextTest extends XyqqSpringbootRedisApplicationTests {

    @Autowired
    private ApplicationContext context;
    @Test
    public void test() {
        // 获取spring上下文
        assertNotNull(context);
        System.out.println("Application context: " + context);

    }
}
