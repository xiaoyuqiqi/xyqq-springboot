package com.xyqq.service.impl;

import com.xyqq.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Test
    void getUserById() {

        System.out.println(userService.getUserById("1"));

    }

    @Test
    void saveUserHash() {
        userService.saveUserHash("1", "name", "zhangsan");
    }

    @Test
    void getUserHash() {
        System.out.println(userService.getUserHash("1", "name"));
    }

    @Test
    void saveUserList() {
        userService.saveUserList("User_List_1", "hello world");
    }

    @Test
    void getUserList() {
        System.out.println(userService.getUserList("User_List_1", 0));
    }

    @Test
    void saveUserSet() {
        userService.saveUserSet("User_Set_1", "hello world");
    }

    @Test
    void getUserSet() {
        System.out.println(userService.getUserSet("User_Set_1"));
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