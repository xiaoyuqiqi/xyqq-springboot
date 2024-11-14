package com.xyqq.service;

public interface RedissonService {

    void testLock();

    String get(String key);
}
