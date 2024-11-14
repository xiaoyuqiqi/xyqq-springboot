package com.xyqq.service;

public interface RedisService {
    public void set(String key,String value);
    public String get(String key);
}
