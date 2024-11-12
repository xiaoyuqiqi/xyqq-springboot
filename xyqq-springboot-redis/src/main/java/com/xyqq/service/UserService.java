package com.xyqq.service;

import java.util.Set;

/**
 * @author xyqq
 */
public interface UserService {

    /**
     * 获取 String 数据
     */
    String getStrByKey(String id);

    /**
     * 存储 Hash 数据
     */
    void saveUserHash(String id, String field, String value);

    /**
     * 获取 Hash 数据
     */
    Object getUserHash(String id, String field);

    /**
     * 存储 list 数据    Redis 中的 List 数据结构本身存储的是字符串值
     */
    void saveUserList(String key, String value);

    /**
     * 获取 list 数据
     */
    Object getUserList(String key, long index);

    /**
     * 存储 set 数据
     */
    void saveUserSet(String key, String value);

    /**
     * 获取 set 数据
     */
    Set<Object> getUserSet(String key);

    /**
     * 存储 zset 数据
     */
    void saveUserZSet(String key, String value, double score);

    /**
     * 获取 zset 数据
     */
    Set<Object> getUserZSet(String key, double min, double max);

}
