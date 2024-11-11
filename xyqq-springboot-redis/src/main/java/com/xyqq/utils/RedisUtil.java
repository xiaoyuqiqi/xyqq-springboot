package com.xyqq.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * Redis工具类
 *
 * @author xyqq
 * @version 1.0.0 (GitHub文档: https://github.com/xiaoyuqiqi/xyqq-springboot/tree/main/xyqq-springboot-redis )<br>
 */
@Component
public class RedisUtil {


    /**
     * 静态化工具类变量
     */
    private static RedisUtil redisUtil;


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @PostConstruct
    public void initBefore() {
        // 工具类的实例赋值给 instanceIocBeanUtil
        redisUtil = this;
        redisUtil.redisTemplate = this.redisTemplate;
    }


    /**
     * 获取列表指定范围内的元素
     *
     * @param key   redis key
     * @param start 开始位置, 0是开始位置
     * @param end   结束位置, -1返回所有
     * @return List<Object>
     */
    public static List<Object> lRange(String key, long start, long end) {
        return redisUtil.redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 删除集合中值等于value得元素
     *
     * @param key   redis key
     * @param index index=0, 删除所有值等于value的元素; index>0, 从头部开始删除第一个值等于value的元素;
     *              index<0, 从尾部开始删除第一个值等于value的元素;
     * @param value 值
     */
    public static void lRemove(String key, long index, String value) {
        redisUtil.redisTemplate.opsForList().remove(key, index, value);
    }

    /**
     * 存储在list头部
     *
     * @param key   redis key
     * @param value 值
     */
    public static void lLeftPush(String key, String value) {
        redisUtil.redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 删除key
     *
     * @param key redis key
     */
    public static void delete(String key) {
        redisUtil.redisTemplate.delete(key);
    }


}
