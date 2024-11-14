package com.xyqq.service.impl;

import com.xyqq.service.RedissonService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xyqq
 */
@Service
public class RedissonServiceImpl implements RedissonService {

    @Autowired
    RedissonClient redisson;


    @Override
    public String get(String key) {

        return "";
    }

    public void testLock()
    {
        // 测试分布式锁


        //锁的名字。 锁的粒度，越细越快。
        //锁的粒度：具体缓存的是某个数据，11-号商品；  product-11-lock product-12-lock   product-lock
        RLock lock = redisson.getLock("product-11-lock");
        lock.lock();

        // 尝试加锁，最多等待 100 秒，上锁以后 10 秒自动解锁
        // boolean res = lock.tryLock(100, 10, TimeUnit.SECONDS);


        try {
            System.out.println("加锁成功=====================执行业务代码================");
            Thread.sleep(30000);
        } catch (Exception e) {

        } finally {
            System.out.println("释放锁..."+Thread.currentThread().getId());
            lock.unlock();
        }
    }
}
