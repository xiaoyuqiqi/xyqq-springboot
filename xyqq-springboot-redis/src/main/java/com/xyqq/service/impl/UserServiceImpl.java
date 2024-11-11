package com.xyqq.service.impl;

import com.xyqq.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * @author xyqq
 */
@Service
public class UserServiceImpl  implements UserService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Override
    public String getUserById(String id) {

        String user = (String) redisTemplate.opsForValue().get("User_" + id);
        StringUtils.isEmpty(user);
        if (StringUtils.isEmpty(user)){
            //模拟从数据库中获取数据
            user = "User_" + id;
            //将数据存入redis中
            redisTemplate.opsForValue().set("User_" + id,user);

            // 可以设置缓存的有效期 redisTemplate.opsForValue().set("User_" + id, user, Duration.ofMinutes(30));，避免缓存长期占用内存

        }

        return "User_" + id;
    }



/*    以下方法演示了如何在 Redis 中使用 RedisTemplate 存取不同的数据结构：

    Hash：使用 opsForHash() 方法操作哈希。
    List：使用 opsForList() 方法操作列表。
    Set：使用 opsForSet() 方法操作集合。
    ZSet：使用 opsForZSet() 方法操作有序集合。*/


    /**
     * 存储 Hash 数据
     */

    @Override
    public void saveUserHash(String id, String field, String value) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        hashOps.put("User_Hash_" + id, field, value);
    }

    /**
     * 获取 Hash 数据
     */
    @Override
    public Object getUserHash(String id, String field) {
        HashOperations<String, Object, Object> hashOps = redisTemplate.opsForHash();
        return hashOps.get("User_Hash_" + id, field);
    }


    /**
     * 存储 List 数据
     */
    @Override
    public void saveUserList(String key, String value) {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        listOps.rightPush("User_List_" + key, value);
    }

    /**
     * 获取 List 数据
     */
    @Override
    public Object getUserList(String key, long index) {
        ListOperations<String, Object> listOps = redisTemplate.opsForList();
        return listOps.index("User_List_" + key, index);
    }

    /**
     * 存储 Set 数据
     */
    @Override
    public void saveUserSet(String key, String value) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        setOps.add("User_Set_" + key, value);
    }

    /**
     * 获取 Set 数据
     */
    @Override
    public Set<Object> getUserSet(String key) {
        SetOperations<String, Object> setOps = redisTemplate.opsForSet();
        return setOps.members("User_Set_" + key);
    }


    /**
     * 存储 ZSet 数据
     */
    @Override
    public void saveUserZSet(String key, String value, double score) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        zSetOps.add("User_ZSet_" + key, value, score);
    }

    /**
     * 获取 ZSet 数据
     */
    @Override
    public Set<Object> getUserZSet(String key, double min, double max) {
        ZSetOperations<String, Object> zSetOps = redisTemplate.opsForZSet();
        return zSetOps.rangeByScore("User_ZSet_" + key, min, max);
    }
}
