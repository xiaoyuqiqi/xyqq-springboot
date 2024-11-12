package com.xyqq.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.xyqq.XyqqSpringbootRedisApplicationTests;
import com.xyqq.model.User;
import com.xyqq.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

class UserServiceImplTest extends XyqqSpringbootRedisApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    void getStrByKey() {

        System.out.println(userService.getStrByKey("1"));

    }

    @Test
    void saveUserHash() {
        userService.saveUserHash("1", "id", "1");
        userService.saveUserHash("1", "name", "zhangsan");

        userService.saveUserHash("2", "id", "2");
        userService.saveUserHash("2", "name", "lisi");
    }

    @Test
    void getUserHash() {
        System.out.println(userService.getUserHash("1", "name"));
    }

    @Test
    void saveUserList() {
        User user1 = new User(1L, "zhangsan");
        User user2 = new User(2L, "lisi");

        // Redis 中的 List 数据结构本身存储的是字符串值

        // 把 User 对象转换为 JSON字符串
        String user1Str = JSONObject.toJSONString(user1);
        String user2Str = JSONObject.toJSONString(user2);


        userService.saveUserList("1", user1Str);
        userService.saveUserList("1", user2Str);
    }

    @Test
    void getUserList() {
        System.out.println(userService.getUserList("1", 0));
    }

    @Test
    void saveUserSet() {
        userService.saveUserSet("1", "hello world");
    }

    @Test
    void getUserSet() {
        System.out.println(userService.getUserSet("1"));
    }

    @Test
    void saveUserZSet() {
//        将 Player1 添加到 ZSet，分数为 50.0
        userService.saveUserZSet("1", "Player1", 50.0);
        userService.saveUserZSet("1", "Player2", 80.0);
    }

    @Test
    void getUserZSet() {
        // 获取分数在 30.0 到 60.0 之间的所有元素。
        Set<Object> userZSet = userService.getUserZSet("1", 30.0, 60.0);
        for (Object o : userZSet) {
            System.out.println(o);
        }

    }
}